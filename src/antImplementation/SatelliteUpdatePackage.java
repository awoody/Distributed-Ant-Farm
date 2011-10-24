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
		String updateText = "SatelliteUpdatePackage from " + this.nodeId() + " containing " + updatedBlocks.size() + " updated blocks for master.";
		
		for(AbstractBlock block : updatedBlocks)
		{
			updateText += "  " + block;
		}
		
		return updateText;
	}
}
