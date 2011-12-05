package communication;

import java.io.Serializable;

/**
 * A wrapper class for an integer which represents a node
 * connected to the network.  That node can be either a client,
 * a distributor, a satellite server or the master server.  Each
 * should have its own unique nodeId associated with it.  I decided
 * to make this a class because we'll almost certainly want more functionality
 * in this object as time goes on.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class NodeId implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6331996288967491489L;
	private int nodeId; //The id of this node
	private int parentNodeId; //The id of this node's parent.
	
	public NodeId(int node, int parent)
	{
		this.nodeId = node;
		this.parentNodeId = parent;
	}
	
	public void setId(int Id)
	{
		nodeId = Id;
	}
	
	public int getId()
	{
		return nodeId;
	}

	public String toString()
	{
		return parentNodeId + "|" + nodeId;
	}
	
	public int getParentNodeId() 
	{
		return parentNodeId;
	}

	public void setParentNodeId(int parentNodeId) 
	{
		this.parentNodeId = parentNodeId;
	}

	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + nodeId;
		result = prime * result + parentNodeId;
		return result;
	}

	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NodeId other = (NodeId) obj;
		if (nodeId != other.nodeId)
			return false;
		if (parentNodeId != other.parentNodeId)
			return false;
		
		return true;
	}
}
