package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import antImplementation.AntInitializationPackage;

import utilities.A;


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
 *
 */
public abstract class AbstractPortal implements Portal, Runnable
{
	protected Recipient recipient;
	protected NodeId nodeId;
	
	/**
	 * Given an abstract package and a set of recipients, uses the NodeIds
	 * in the set to ensure that every recipient in the list recieves the package
	 * by transferring that package over the socket corresponding to                              
	 */
	@Override
	public abstract void dispatchPackage(AbstractPackage aPackage, List<NodeId> recipients); 

	@Override
	public abstract void dispatchDirectlyToMaster(AbstractPackage aPackage); 
	
	
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
	

	/* (non-Javadoc)
	 * @see communication.Portal#setRecipient(communication.Recipient)
	 */
	public void setRecipient(Recipient aRecipient)
	{
		recipient = aRecipient;
	}
	
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		
	}
	
	public class NodeConnection implements Runnable
	{
		private Socket s;
		private boolean isOpen;
		private ObjectInputStream inputStream;
		private ObjectOutputStream outputStream;
		
		public NodeConnection(Socket s)
		{
			this.s = s;		
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
							
							AbstractPackage p;

							p = (AbstractPackage) o;
							
							if(p instanceof InitializationPackage)
							{								
								nodeId = ((InitializationPackage) p).getIdForNewNode();
								A.say("Node Connection received package.  Setting nodeId to " + nodeId);
							}
							
							if(p instanceof DistributorPackage)
							{
								//If this is a distributor package, this connection must be totally
								//reset on a new port.
								resetNodeConnection(((DistributorPackage) p).getNewPortId());
							}
								
							recipient.receivePackage(p);
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
		
		
		public void send(AbstractPackage o)
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
				outputStream.writeObject(o);
				A.say("Sent a package from " + nodeId);
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
		
	}
}
