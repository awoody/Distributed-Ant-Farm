package communication;

import utilities.A;

/**
 * This interface simply indicates that an object is capable of
 * receiving and processing an abstract package.  Each portal should
 * have exactly one recipient and vice-versa.  It also has a method
 * which accepts a connection from a new client and returns a package
 * meant to inform the new client of it's state, taking that new client's
 * nodeId as a parameter.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public interface Recipient 
{
	/**
	 * Hands the abstract package off to the recipient for processing.
	 * 
	 * @param aPackage
	 */
	public void receivePackage(AbstractPackage aPackage);
	
	/**
	 * Indicates that a new connection has arrived at the recipient's
	 * portal, and that the recipient should generate a new abstractPackage
	 * tailored towards a new connection.  In the case of the ant model, this
	 * would be a new package for the Ant indicating its location and Id on the
	 * Ant grid, etc.
	 * 
	 * @param id
	 * @return 
	 * 		- An abstract package for the new connection, informing the new engine
	 * 			of it's state.
	 */
	public AbstractPackage packageForNewConnection(NodeId id);
}
