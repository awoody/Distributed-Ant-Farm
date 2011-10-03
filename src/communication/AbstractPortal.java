package communication;

import java.net.Socket;
import java.util.Map;
import java.util.Set;


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
	private Recipient recipient;
	private NodeId nodeId;
	private Map<NodeId, Socket> allConnectedNodes;
	
	/**
	 * Given an abstract package and a set of recipients, uses the NodeIds
	 * in the set to ensure that every recipient in the list recieves the package
	 * by transferring that package over the socket corresponding to that nodeId
	 * in the all connected nodes map.
	 */
	@Override
	public void dispatchPackage(AbstractPackage aPackage, Set<NodeId> recipients) 
	{
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets the nodeId of this portal.
	 * 
	 * @param id - This id of this portal.
	 */
	public void setNodeId(NodeId id) 
	{
		// TODO Auto-generated method stub
	}

	
	/* (non-Javadoc)
	 * @see communication.Portal#getNodeId()
	 */
	public NodeId getNodeId() 
	{
		return nodeId;
	}
	
	/* (non-Javadoc)
	 * @see communication.Portal#getAllConnectedNodes()
	 */
	public Set<NodeId> getAllConnectedNodes()
	{
		return allConnectedNodes.keySet();	
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
}
