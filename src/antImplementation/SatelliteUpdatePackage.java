package antImplementation;

import java.util.List;

import communication.NodeId;

public class SatelliteUpdatePackage extends AntSatelliteToMasterPackage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9075900622427851434L;
	private List<AbstractBlock> updatedBlocks;
	private int lastKnownAntId;

	public SatelliteUpdatePackage(NodeId id, List<AbstractBlock> updatedBlocks, int lastKnownAntId)
	{
		super(id);
		this.updatedBlocks = updatedBlocks;
		this.lastKnownAntId = lastKnownAntId;
	}

	public int getLastKnownAntId()
	{
		return lastKnownAntId;
	}
	
	public List<AbstractBlock> getUpdatedBlocks()
	{
		return updatedBlocks;
	}
	
	public String toString()
	{
		return "SatelliteUpdatePackage from " + this.nodeId() + " containing updated blocks for master.";
	}
}
