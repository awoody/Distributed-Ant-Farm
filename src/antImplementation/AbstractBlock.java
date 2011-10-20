package antImplementation;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBlock implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8965921168807705079L;
	private static ModificationQueue modificationQueue;
	private Point location;
	private List<Integer> allAntsThisCell;
	private List<Pheromone> allPharomonesThisBlock;
	private Food food;
		
	public static void setModQueue(ModificationQueue aQueue)
	{
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
			return;
		
		this.allAntsThisCell = newBlock.allAntsThisCell;
		this.allPharomonesThisBlock = newBlock.allPharomonesThisBlock;
		this.food = newBlock.food;
		modificationQueue.addAbstractBlockToModQueue(this);
	}
	
	public synchronized void removeAnt(int antId)
	{
		Integer antIdO = new Integer(antId);
		allAntsThisCell.remove(antIdO);
		modificationQueue.addAbstractBlockToModQueue(this);
	}
	
	public synchronized void addAnt(int antId)
	{
		Integer antIdO = new Integer(antId);
		allAntsThisCell.add(antIdO);
		modificationQueue.addAbstractBlockToModQueue(this);
	}
	
	public Point getLocation()
	{
		return location;
	}
	
	public void setLocation(Point location)
	{
		this.location = location;
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

		if(other.allPharomonesThisBlock != this.allPharomonesThisBlock)
			return false;
		
		if(other.allAntsThisCell != this.allAntsThisCell)
			return false;
		
		//TODO: Maintain this method as more fields are added.
		return true;
	}
	
	
}
