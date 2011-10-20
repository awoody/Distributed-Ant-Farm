package antImplementation;

import communication.NodeId;

public class SatelliteInitializationPackage extends AntMasterToSatellitePackage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8110552267140842491L;
	private AbstractBlock [][] allBlocks;
	private int lastKnownAntId;
	
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
}
