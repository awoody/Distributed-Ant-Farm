package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import rpc.AnnotatedObject;
import rpc.RPCInjectionModule;
import utilities.A;

import com.google.inject.Guice;
import com.google.inject.Injector;

import constants.iConstants;
import distributor.DistributorInterface;
import distributor.NetworkResource.NetworkLocation;
import distributor.iDistributor;


/**
 * Abstract class representing a portal object, which is an object
 * capable of communicating via network sockets with other objects on 
 * the network.  This exists at its highest level as an iface, so that
 * other objects needing to communicate via portals aren't aware of the 
 * complexity of the implementation.  The abstract class exists so that data
 * structures and methods common to Client, Distributor, SatelliteServer and 
 * Master can be implemented in once place, rather than multiple times over.
 * Specific changes needed for those classes to common methods can be done
 * by overriding at the implementation level.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
/**
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 * @param <E>
 * @param <E>
 * @param <E>
 *
 */
public abstract class Portal implements iPortal
{
	protected NodeId nodeId;
	protected Map<NodeId, NodeConnection> allConnections;
	protected Injector injector;
	protected Recipient recipient;
	protected DistributorInterface distributor;
	protected iConstants constants;
	
	public Portal(Recipient r, iConstants constants)
	{
		this.recipient = r;
		this.constants = constants;
		allConnections = new HashMap<NodeId, NodeConnection>();
		injector = Guice.createInjector(new RPCInjectionModule(this));
	}
	
	
	protected void connectToDistributor()
	{		
		Socket connection;
		AnnotatedObject distributor = null;
		try 
		{
			connection = new Socket(constants.getDefaultDistributorHostName(), constants.getDefaultDistributorPort());
			NodeConnection distributorConnection = new NodeConnection(connection);
			
			Thread distributorThread = new Thread(distributorConnection);
			distributorThread.start();
			
			NodeId distributorNodeId = constants.getDistributorNodeId();
			allConnections.put(distributorNodeId, distributorConnection);
			
			distributor = injector.getInstance(DistributorInterface.class);
			distributor.setNodeId(distributorNodeId);
			
			this.distributor = (DistributorInterface) distributor;
		} 
		catch (UnknownHostException e) 
		{
			A.error("Unable to connect to distributor");
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			A.error("Unable to connect to distributor");
			e.printStackTrace();
		}		
	}
	
	
	@Override
	public AnnotatedObject makeNewConnection(String annotatedObjectName) 
	{
		NetworkLocation objectLocation = distributor.networkLocationForString(annotatedObjectName);
		
		try 
		{
			Socket connection = new Socket(objectLocation.getAddress(), objectLocation.getPortNumber());
			
			NodeConnection newConnection = new NodeConnection(connection);
			Thread connectionThread = new Thread(newConnection);
			connectionThread.start();
			
			allConnections.put(objectLocation.getNodeId(), newConnection);
			
			@SuppressWarnings("unchecked")
			AnnotatedObject object = injector.getInstance(constants.getAnnotatedObjectsByString().get(annotatedObjectName));
			object.setNodeId(objectLocation.getNodeId());
			
			return object;		
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public iDistributor distributorConnection()
	{
		if(distributor == null)
		{
			this.connectToDistributor();
		}
		
		return distributor;
	}
	
	
	public void dispatchAsynchronousPackage(AbstractPackage aPackage,
			NodeId recipient)
	{
		NodeConnection connection = allConnections.get(recipient);
		connection.sendAsynchronousPackage(aPackage);
	}


	public Object dispatchSynchronousPackage(AbstractPackage aPackage,
			NodeId recipient) 
	{
		
		NodeConnection connection = allConnections.get(recipient);
		
		if(connection != null)
			return connection.sendSynchronousPackage(aPackage);
		
		return null;
	}
	
	
	/**
	 * Sets the nodeId of this portal.
	 * 
	 * @param id - This id of this portal.
	 */
	public void setNodeId(NodeId id) 
	{
		nodeId = id;
	}

	/* (non-Javadoc)
	 * @see communication.Portal#getNodeId()
	 */
	public NodeId getNodeId() 
	{
		return nodeId;
	}
	
	
	public MessageId generateMessageId()
	{
		return new MessageId(nodeId);
	}
	
	
	
	
	
	public class NodeConnection implements Runnable
	{
		private Socket s;
		private boolean isOpen;
		private ObjectInputStream inputStream;
		private ObjectOutputStream outputStream;
		private final Map<MessageId, SynchronousCallHolder> outstandingSynchronousCalls;
		
		
		public NodeConnection(Socket s)
		{
			this.s = s;		
			outstandingSynchronousCalls = new HashMap<MessageId, SynchronousCallHolder>();
			
			//Establish input and output streams over the socket.
			try 
			{
				outputStream = new ObjectOutputStream(s.getOutputStream());
				inputStream = new ObjectInputStream(s.getInputStream());
				isOpen = true;
				
			} 
			catch (IOException e) 
			{
				A.say("Warning, the requested client connection was not properly initialized!");
				isOpen = false;
				e.printStackTrace();
			}
		}
		
		
		@Override
		public void run() 
		{	
			while(isOpen)
			{	
				try 
				{
					Object o;
					try 
					{
						if((o = inputStream.readObject()) != null)
						{
							A.say("Reading from input stream");
							AbstractPackage p = (AbstractPackage) o;
							
							if(p instanceof ResourceIdentificationPackage)
							{
								ResourceIdentificationPackage rip = (ResourceIdentificationPackage) p;
								
								if(rip.isReturningToSender())
								{
									SynchronousCallHolder holder = outstandingSynchronousCalls.remove(rip.messageId());
									
									Object [] response = {rip.getResourceNodeId(), rip.getResourceObjectName()};
									
									holder.setReturnValue(response);
									holder.continueThread();
								}
								else
								{
									rip.setResourceNodeId(getNodeId());
									rip.setResourceObjectName(recipient.getResourceName());
									
									rip.flip();
									this.sendAsynchronousPackage(rip);
								}
							}							
							else if(p instanceof InitializationPackage)
							{								
								nodeId = ((InitializationPackage) p).getIdForNewNode();
								
								if(recipient != null)
									recipient.setNodeId(nodeId);
								
								A.say("Node Connection received init package.  Setting nodeId to " + nodeId);
							}														
							else if(p instanceof InvocationPackage)
							{
								
								InvocationPackage ip = (InvocationPackage) p;
								A.say("Recived an invocation package " + ip);
								
								//Thread all of the invocation packages off so that 
								//the user methods that invoke them are able to make
								//synchronous RPC from within their methods.  Otherwise,
								//deadlock would result as this listener thread got deadlocked
								//waiting for input to come in.
								RunnableInvocation ri = new RunnableInvocation(ip);
								Thread invocationThread = new Thread(ri);
								invocationThread.start();
							}
							else if(p instanceof ResponsePackage)
							{
								A.say("Received a response package");
								ResponsePackage response = (ResponsePackage) p;
								
								//This package must contain a response to something which was sent out at
								//an earlier point; there is a thread waiting on it, so first set the 
								//thread's holder's return value, and then resume the thread.
								SynchronousCallHolder holder = outstandingSynchronousCalls.remove(response.messageId());
								holder.setReturnValue(response.getReturnValue());
								holder.continueThread();
							}
							
						}
					}
					catch (ClassNotFoundException e) 
					{
						e.printStackTrace();
					}
				} 
				catch(IOException e)
				{
				
				}
			}

		}
		
		public void resetNodeConnection(int newPort)
		{
			isOpen = false; //close the connection.
			s = null;
			outputStream = null;
			inputStream = null;
						
			try 
			{
				A.say("Will attempt to open new connection to: " + newPort);
				s = new Socket("localhost", newPort);	
				outputStream = new ObjectOutputStream(s.getOutputStream());
				inputStream = new ObjectInputStream(s.getInputStream());
				isOpen = true;
			} 
			catch (IOException e) 
			{
				A.say("Warning, failed to properly reset the nodeConnection.  Socket is not open.");
				isOpen = false;
				e.printStackTrace();
			}
			
		}
		
		
		public NodeId getNodeId()
		{
			return nodeId;
		}
		
				
		public Object sendSynchronousPackage(AbstractPackage aPackage)
		{
			//Without a node id, you can only receive.
			//If you aren't open, however, you can't do anything.
			if(nodeId == null || !isOpen) 
			{
				A.say("Cannot send packages without valid nodeId");
				return null;
			}
			
			Object o = null;
			try 
			{
				outputStream.writeObject(aPackage);
				A.say("Sent a synchronous package from " + nodeId + " The package was: " + aPackage.toString());
				
				
				SynchronousCallHolder holder = new SynchronousCallHolder(Thread.currentThread());
				outstandingSynchronousCalls.put(aPackage.messageId(), holder);
				A.say("Blocking this thread");
				holder.holdThread(); //This will cause execution to block here until the message returns.
				
				//When execution gets to here, it means the holder's return value has been set by the listener
				//thread and the thread which was trapped here has been resumed.  Good times.  We can return
				//from this RPC with our return value and continue on our merry way.
				o = holder.getReturnValue();
				A.say("Thread was unblocked.");
			} 
			catch (IOException e) 
			{
				A.say("Unable to send the requested object from a Node Connection");
				e.printStackTrace();
			}
			
			return o;
		}
		
		
		public void sendAsynchronousPackage(AbstractPackage aPackage)
		{
			//Without a node id, you can only receive.
			//If you aren't open, however, you can't do anything.
			if(nodeId == null || !isOpen) 
			{
				A.say("Cannot send packages without valid nodeId");
				return;
			}
			
			try 
			{
				outputStream.writeObject(aPackage);
				A.say("Sent am asynchronous package from " + nodeId + " The package was: " + aPackage.toString());
			} 
			catch (IOException e) 
			{
				A.say("Unable to send the requested object from a Node Connection");
				e.printStackTrace();
			}
		}
		
		
		public void closeConnection()
		{
			try 
			{
				s.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				isOpen = false;
			}
			
		}
		
		private class RunnableInvocation implements Runnable
		{
			private InvocationPackage ip;
			
			public RunnableInvocation(InvocationPackage ip)
			{
				this.ip = ip;
			}

			@Override
			public void run() 
			{
				if(ip.isSynchronous())
				{
					Object returnValue = recipient.invokeMethod(ip.getMethodName(), ip.getArguments());
					
					//Create a response package with the same message id since there is a thread waiting on
					//this return value on the other side.
					ResponsePackage responsePackage = new ResponsePackage(nodeId, ip.getMessageId(), returnValue);
					
					//Dispatch the package back where it came from.
					sendAsynchronousPackage(responsePackage);;
				}
				else
				{
					recipient.invokeMethod(ip.getMethodName(), ip.getArguments());
				}
			}
		}
		
	}

}
