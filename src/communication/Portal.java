package communication;

import java.util.Set;
import utilities.A;

/**
 * Portal is the interface implemented to allow objects to make use of the
 * server infrastructure by sending out an AbstractPackage to designated recipients.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public interface Portal 
{
	/**
	 * Given a package and a list of recipients, serializes that package and sends it
	 * to all recipients in the recipient list.
	 * 
	 * @param aPackage - The Package to Send.
	 * @param recipients - A set of nodeId objects designating the recipients of the package.
	 */
	public void dispatchPackage(AbstractPackage aPackage, Set<NodeId>recipients);
	
	/**
	 * Returns the nodeId of this portal.
	 */
	public NodeId getNodeId();
	
	/**
	 * Returns the nodeId of all portals which are connected to this portal.  For example,
	 * a satelliteServer would return the nodeIds of all connected clients, as well as the 
	 * master server.  The master server would return all of the satellite servers.
	 */
	public Set<NodeId> getAllConnectedNodes();
	
	/**
	 * Sets the portal's recipient as passed in.  For all incoming packages to this
	 * portal, the designated recipient will have those packages forwarded to it for 
	 * processing.
	 * 
	 * @param aRecipient
	 */
	public void setRecipient(Recipient aRecipient);
}
