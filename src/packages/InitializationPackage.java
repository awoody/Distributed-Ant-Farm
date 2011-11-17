package packages;

import communication.NodeId;

public class InitializationPackage extends AbstractPackage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3419652946052350500L;
	public NodeId idForNewNode;
	
	public InitializationPackage(NodeId id, MessageId messageId, NodeId idForNewNode) 
	{
		super(id, messageId);
		this.idForNewNode = idForNewNode;
		// TODO Auto-generated constructor stub
	}

	public NodeId getIdForNewNode()
	{
		return idForNewNode;
	}

	@Override
	public String toString() 
	{
		// TODO Auto-generated method stub
		return "Initialization Package Going to Node: " + idForNewNode;
	}
}
