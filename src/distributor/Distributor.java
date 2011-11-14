package distributor;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import rpc.AnnotatedObject;
import utilities.A;

import communication.AbstractPackage;
import communication.InitializationPackage;
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
	
	public Distributor(iConstants constants)
	{
		super(null, constants);
		
		try 
		{
			serverSocket = new ServerSocket(constants.getDefaultDistributorPort());
			resourcesByName = new HashMap<String, NetworkResource>();
			nodeId = constants.getDistributorNodeId();
			isRunning = true;
	
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
				
				A.say("Distributor accepted a new connection and assigned nodeId: " + ip.idForNewNode);
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
		public NetworkLocation networkLocationForString(String objectName)
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
			
			return returnValue;
		}

		@Override
		public String returnTestString()
		{
			System.out.println("Creating test string");
			return "Distributor test string!! Holy crap, it's alive.";
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
	}
}
