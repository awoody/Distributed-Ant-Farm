package distributor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import monitor.Graph;
import monitor.Node;
import monitor.iNodeStatus;
import packages.AbstractPackage;
import packages.InitializationPackage;
import packages.ResourceUpdatePackage;
import rpc.AnnotatedObject;
import utilities.A;

import communication.NodeId;
import communication.Portal;
import communication.Recipient;

import constants.iConstants;
import distributor.NetworkResource.NetworkLocation;

public class Distributor extends Portal implements Runnable
{
	private ServerSocket serverSocket;
	int totalSatellites = 1;
	private boolean isRunning;
	private static int nextNodeId = 1;
	private Map<String, NetworkResource> resourcesByName;
	private ExecutorService updateRequestThreadPool;
	private Graph nodeGraph;
//	private int updateRequestsDispatched = 0;
//	private int updateRequestsReceived = 0;
	
	public Distributor(iConstants constants)
	{
		super(null, constants);
		
		try 
		{
			nodeGraph = new Graph();
			
			serverSocket = new ServerSocket(constants.getDefaultDistributorPort());
			resourcesByName = new ConcurrentHashMap<String, NetworkResource>();
			updateRequestThreadPool = Executors.newCachedThreadPool();
			nodeId = constants.getDistributorNodeId();
			isRunning = true;
			nodeGraph.setDistributor(new Node(nodeId));
			nodeGraph.addNode(nodeId);
	
			//Kind of a hack but this is the only case where the recipient
			//is the object itself and not a user-provided object.
			DistributorRecipient recipient = new DistributorRecipient();
			recipient.reMapMethods();
			this.recipient = recipient;
			
			Thread threadForMe = new Thread(this);
			threadForMe.start();

			
			A.log("Distributor is running.");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}	
	}
	
	
	@Override
	public NetworkLocation getNetworkLocation()
	{
		// TODO Auto-generated method stub
		return new NetworkLocation(constants.getDefaultDistributorPort(), A.getSiteLocalAddress(), nodeId);
	}


	@Override
	public boolean isServer()
	{
		// TODO Auto-generated method stub
		return true;
	}
	
	
	public Graph getNetworkGraph()
	{
		return null;
	}
	
	
	public void run() 
	{		
		Socket clientSocket;
		try 
		{
			while (isRunning)
			{
				clientSocket = serverSocket.accept();
				A.say("Distributor is processing a new connection.");
				// When something connects to the distributor, create a
				// distributor package with the port of the appropriate
				// satellite server for it to connect to.
				A.say("Distributor processing a new connection.");
				NodeConnection newConnection = new NodeConnection(clientSocket);
				Thread newClientThread = new Thread(newConnection);
				newClientThread.start();
				InitializationPackage ip = new InitializationPackage(nodeId, this.generateMessageId(), this.generateNewNodeId()); 
				
				this.allConnections.put(ip.getIdForNewNode(), newConnection);
				newConnection.sendAsynchronousPackage(ip);
				
				//Add the new node and then add its edges between the distributor
				//and the new node.
				nodeGraph.addNode(ip.idForNewNode);
				nodeGraph.addEdge(nodeId, ip.getIdForNewNode());
				nodeGraph.addEdge(ip.getIdForNewNode(), nodeId);
				
				A.log("Distributor accepted a new connection and assigned nodeId: " + ip.idForNewNode);
			}
		}
		catch (IOException e) 
		{
			A.say("Failed to make a connection to a client");
		}
	}
	
	
	public NodeId generateNewNodeId()
	{
		return new NodeId(nextNodeId++, 0);
	}
	
	
	public NodeId getNodeId()
	{
		return nodeId;
	}


	@Override
	public void dispatchAsynchronousPackage(AbstractPackage aPackage,
			NodeId recipient) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object dispatchSynchronousPackage(AbstractPackage aPackage,
			NodeId recipient)
	{
		// TODO Auto-generated method stub
		return null;
	}
	
	public class DistributorRecipient extends Recipient implements iDistributor
	{
		@Override
		public NetworkLocation connectionToResourceForNode(NodeId requestingNode, String objectName)
		{
			if(objectName == null)
				A.error("A null object name was given");
			
			NetworkResource resource = resourcesByName.get(objectName);
			
			if(resource == null)
			{	
				A.error("There is no registered resource for: " + objectName + " please verify you are asking for the correct resource in your constants file, and that the resource is capable of accepting incoming connections.");
				return null;
			}
			
			NetworkLocation returnValue = resource.getNetworkLocation();
			
			if(returnValue == null)
				A.error("There are no location for the resource.  This indicates somethign odd has happened: " + objectName);
			
			//Adds a bi-directional edge between the two nodes since the socket that will
			//be created goes both ways.
			nodeGraph.addEdge(requestingNode, returnValue.getNodeId());
			nodeGraph.addEdge(returnValue.getNodeId(), requestingNode);
			return returnValue;
		}

		
		public void registerNetworkResource(String resourceName, int port, String address, NodeId id)
		{
			if(!resourcesByName.containsKey(resourceName))	
			{
				NetworkResource newResource = new NetworkResource(resourceName);
				resourcesByName.put(resourceName, newResource);
			}

			resourcesByName.get(resourceName).addLocation(port, address, id);
			
			A.say("Distributor registered a new resource: " + resourcesByName.get(resourceName));
		}
		
		
		public void reMapMethods()
		{
			methodMap = A.mapMethods(this);
		}
		
		
		@Override
		public void newObjectHasConnected(AnnotatedObject newObject) 
		{
			
		}

		@Override
		public String getResourceName() 
		{
			return null;
		}


		@Override
		public Graph getObjectGraph()
		{
			
			//This is threaded off using a cached thread pool
			//because it was too laggy doing this in a loop;
			//the requesting thread was blocking for longer
			//than the allowed timeout time as a result just
			//waiting for this IO to complete.
			for(NodeId id : allConnections.keySet())
			{
				
				UpdateRequestDispatcher urd = new UpdateRequestDispatcher(id);
				updateRequestThreadPool.submit(urd);
				//updateRequestsDispatched++;
			}
			
			//Little trick to handle the fact that the last update requests threaded
			//off to the pool might not ever return in time to get returned in this
			//call; the updates to the graph would wind up being always one cycle
			//behind on the later nodes.  So, simply count the received and sent
			//requests and spin until they're equal.
//			while(true)
//			{
//				if(updateRequestsReceived == updateRequestsDispatched)
//				{
//					updateRequestsReceived = 0;
//					updateRequestsDispatched = 0;
//					
//					break;
//				}
//				
//				try 
//				{
//					Thread.sleep(10);
//				}
//				catch (InterruptedException e) 
//				{
//					e.printStackTrace();
//				}
//			}
			
			
			
			return nodeGraph;
		}
		
		
	}
	
	private class UpdateRequestDispatcher implements Runnable
	{
		private final NodeId id;
		
		public UpdateRequestDispatcher(NodeId id)
		{
			this.id = id;
		}
		
		@Override
		public void run()
		{
			NodeConnection c = allConnections.get(id);		
			ResourceUpdatePackage up = new ResourceUpdatePackage(nodeId, generateMessageId());
			iNodeStatus status = (iNodeStatus) c.sendSynchronousPackage(up);
			//updateRequestsReceived++;
			nodeGraph.getNodeMap().get(id).setNodeStatus(status);
		}
		
	}
}
