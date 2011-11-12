package rpc;

import communication.NodeId;

public abstract class AnnotatedObject 
{
	protected NodeId nodeId;
		
	public NodeId getNodeId()
	{
		return nodeId;
	}
	
	public void setNodeId(NodeId i)
	{
		this.nodeId = i;
	}
}
