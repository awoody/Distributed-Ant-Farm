package antImplementation;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import utilities.A;

public abstract class AbstractBlock implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965921168807705079L;
	private static ModificationQueue modificationQueue;
	private final Point location;
	private List<Integer> allAntsThisCell;
	private List<Pheromone> allPharomonesThisBlock;
	private Food food;
		
	public static void setModQueue(ModificationQueue aQueue)
	{
		A.say("Abstract block mod queue set to: " + aQueue);
		modificationQueue = aQueue;
	}
	
	public AbstractBlock(Point location)
	{
		this.location = location;
		allAntsThisCell = new ArrayList<Integer>();
		allPharomonesThisBlock = new ArrayList<Pheromone>();
		
	}
	
	public synchronized void updateFromBlock(AbstractBlock newBlock)
	{
		if(this.equals(newBlock))
			return;
		
		this.allAntsThisCell = newBlock.allAntsThisCell;
		this.allPharomonesThisBlock = newBlock.allPharomonesThisBlock;
		this.food = newBlock.food;
	}
	
	public synchronized void updateFromBlockAndBroadcast(AbstractBlock newBlock)
	{
		if(this.equals(newBlock))
		{
			A.say("No changes discovered in block " + this + " so no changes will be logged.");
			return;
		}
		
		this.allAntsThisCell = newBlock.allAntsThisCell;
		this.allPharomonesThisBlock = newBlock.allPharomonesThisBlock;
		this.food = newBlock.food;
		modificationQueue.addAbstractBlockToModQueue(this);
	}
	
	public synchronized void removeAnt(int antId)
	{
		A.say("Block " + this + " removing ant" + antId);
		Integer antIdO = new Integer(antId);
		allAntsThisCell.remove(antIdO);
		modificationQueue.addAbstractBlockToModQueue(this);
	}
	
	public synchronized void addAnt(int antId)
	{
		A.say("Block " + this + " adding ant " + antId);
		Integer antIdO = new Integer(antId);
		allAntsThisCell.add(antIdO);
		modificationQueue.addAbstractBlockToModQueue(this);
	}
	
	public Point getLocation()
	{
		return location;
	}
			
	public synchronized boolean containsFood()
	{
		return !(food == null);
	}
	
	public synchronized Food getFood()
	{
		return food;
	}
	
	public synchronized void setFood(Food food)
	{
		this.food = food;
	}

	public String toString()
	{
		return "Abstract Block at: " + location.x + " " + location.y + " containing " + allAntsThisCell.size() + " ants. ";
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractBlock other = (AbstractBlock) obj;

		if(!other.allPharomonesThisBlock.equals(this.allPharomonesThisBlock))
			return false;
		
		if(!other.allAntsThisCell.equals(this.allAntsThisCell))
			return false;
		
		//TODO: Maintain this method as more fields are added.
		return true;
	}
	
	
}
