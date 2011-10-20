package antImplementation;

import communication.InitializationPackage;
import communication.NodeId;

public class SatelliteInitializationPackage extends AntMasterToSatellitePackage implements InitializationPackage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8110552267140842491L;
	private AbstractBlock [][] allBlocks;
	private int lastKnownAntId;
	private NodeId idForNewNode;
	
	public SatelliteInitializationPackage(NodeId id, AbstractBlock[][] allBlocks, int lastKnownAntId)
	{
		super(id);
		this.allBlocks = allBlocks;
		this.lastKnownAntId = lastKnownAntId;
	}

	public int getLastKnownAntId()
	{
		return lastKnownAntId;
	}
	
	public AbstractBlock[][] getAllBlocks()
	{
		return allBlocks;
	}
	
	public String toString()
	{
		return "Satellite Initialization Package from " + this.nodeId() + " with updates for new satellite.";
	}

	@Override
	public void setIdForNewNode(NodeId idForNewNode)
	{
		this.idForNewNode = idForNewNode;
	}

	@Override
	public NodeId getIdForNewNode()
	{
		return idForNewNode;
	}
}
