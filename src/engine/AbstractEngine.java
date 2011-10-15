package engine;

import communication.AbstractPackage;
import communication.NodeId;
import communication.Portal;
import communication.Recipient;

/**
 * This is the superclass for all three of the engine objects.  This
 * should contain any details, such as implementation of the recipient
 * protocol, that pertain to any element of the engine.  
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public abstract class AbstractEngine implements Recipient
{
	protected final Portal portal;
	
	/**
	 * All abstract engine constructors must at least
	 * take a portal as a parameter, since each recipient
	 * is bound to a portal and vice-versa.
	 * @param aPortal
	 */
	public AbstractEngine(Portal aPortal)
	{
		portal = aPortal;
		portal.setRecipient(this);
	}
	
	/* (non-Javadoc)
	 * @see communication.Recipient#receivePackage(communication.AbstractPackage)
	 */
	@Override
	public void receivePackage(AbstractPackage aPackage)
	{
		return;
	}

	/**
	 * Returns the portal set for this recipient, so that it's
	 * methods might be accessed.
	 * 
	 * @return
	 * 		- This recipient's portal.
	 */
	public Portal getPortal() 
	{
		return portal;
	}
	
	/**
	 * Returns a specialized package for a new connection.  This should
	 * be overridden in a subclass, since this would be drastically different
	 * for a master engine, a satellite engine and a client engine.
	 */
	public AbstractPackage packageForNewConnection(NodeId id)
	{
		return null;
	}

}
