package antImplementation;

import java.util.List;

import communication.NodeId;

public class MasterUpdatePackage extends AntMasterToSatellitePackage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8816263637667933263L;
	private List<AbstractBlock> updatedBlocks;
	private int lastKnownAntId;
	
	public MasterUpdatePackage(NodeId id, List<AbstractBlock> updatedBlocks, int lastKnownAntId)
	{
		super(id);
		// TODO Auto-generated constructor stub
		
		this.updatedBlocks = updatedBlocks;
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
		return "Master update broadcast package for all satellites.";
	}
}
