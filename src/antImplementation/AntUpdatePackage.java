package antImplementation;

import java.awt.Point;

import communication.NodeId;

public class AntUpdatePackage extends AntSatelliteToClientPackage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1343214087165779547L;
	private AbstractBlock[][] updatedBlocks;
	private Point newLocation;
	private int antId;
	
	
	public AntUpdatePackage(NodeId id, AbstractBlock[][] newSegment, Point newLocation, int antId)
	{
		super(id);
		
		this.updatedBlocks = newSegment;
		this.newLocation = newLocation;
		this.antId = antId;
	}

	public AbstractBlock[][] getUpdatedBlocks()
	{
		return updatedBlocks;
	}

	public Point getNewLocation()
	{
		return newLocation;
	}
	
	public String toString()
	{
		return "Ant Update Package from satellite " + this.nodeId() + " ant " + antId + " is now at " + newLocation.x + " " + newLocation.y;
	}
}
