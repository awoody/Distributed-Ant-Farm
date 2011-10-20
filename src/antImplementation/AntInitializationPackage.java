package antImplementation;

import java.awt.Point;

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
	private int antId;
	private AbstractBlock [][] antSegment;
	private Point startingSpot;
	
	public AntInitializationPackage(NodeId id, int antId, AbstractBlock[][] firstSegment, Point startingLocation) 
	{
		super(id);
		this.antId = antId;
		// TODO Auto-generated constructor stub
	}

	public Point getStartingLocation()
	{
		return startingSpot;
	}
	
	public int getAntId()
	{
		return antId;
	}
	
	public AbstractBlock[][] getSegment()
	{
		return antSegment;
	}
	
	public NodeId getIdForNewNode() 
	{
		return idForNewNode;
	}

	public void setIdForNewNode(NodeId idForNewNode)
	{
		this.idForNewNode = idForNewNode;
	}
	
	public String toString()
	{
		return "Ant Initialization Package from " + this.nodeId() + " assigning antId " + antId + " to a new ant.";
	}
	
}
