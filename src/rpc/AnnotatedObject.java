package rpc;

import communication.NodeId;

/**
 * This object goes hand-in-hand with the recipient
 * object.  They are both intended to be implemented
 * by users developing for this framework, and they 
 * both should have a user interface in common that they
 * implement.
 * 
 * When you have a subclass of this object impelement an
 * interface, you MUST annotate each of the methods with
 * either the Synchronous or the Asynchronous annotation.
 * Any return values or logic that you provide within the 
 * method will be ignored.
 * 
 * When you call a method on this object, the method call and
 * it's parameters are intercepted, and shipped across the network
 * to a connected recipient capable of handling your me
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public abstract class AnnotatedObject 
{
	protected NodeId nodeId;
		
	
	/**
	 * Returns the nodeId used by the network
	 * architecture to identify this node.
	 * 
	 * @return
	 */
	public NodeId getNodeId()
	{
		return nodeId;
	}
	
	
	public void setNodeId(NodeId i)
	{
		this.nodeId = i;
	}
}
