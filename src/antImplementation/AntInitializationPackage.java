package antImplementation;

import communication.AbstractPackage;
import communication.InitializationPackage;
import communication.NodeId;

public class AntInitializationPackage extends AbstractPackage implements InitializationPackage
{
	private NodeId idForNewNode;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1849068562116179211L;

	public AntInitializationPackage(NodeId id) 
	{
		super(id);
		// TODO Auto-generated constructor stub
	}

	public NodeId getIdForNewNode() 
	{
		return idForNewNode;
	}

	public void setIdForNewNode(NodeId idForNewNode)
	{
		this.idForNewNode = idForNewNode;
	}
	
	
	
}
