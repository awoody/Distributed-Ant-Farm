package antImplementation;

import java.awt.Point;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import utilities.A;
import communication.AbstractPackage;
import communication.NodeId;
import communication.Portal;
import engine.AbstractMasterEngine;

/**
 * This is the ant-farm implementation of the AbstractMasterEngine object.
 * This is where the general distributed server ends and the ant farm 
 * begins.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class MasterAntFarm extends AbstractMasterEngine implements ModificationQueue
{
	private AbstractBlock[][] allBlocks;
	private int gridSize = 32;
	private List<AbstractBlock> abstractBlockModQueue;
	private int lastKnownAntId;
	
	public MasterAntFarm(Portal aPortal) 
	{
		super(aPortal);
		// TODO Auto-generated constructor stub
		
		abstractBlockModQueue = new LinkedList<AbstractBlock>();
		AbstractBlock.setModQueue(this);
		
		
		allBlocks = new AbstractBlock[gridSize][gridSize];
		for(int i=0; i < gridSize; i++)
		{
			for(int j=0; j < gridSize; j++)
			{
				allBlocks[i][j] = new GenericBlock(new Point(j, i));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see engine.AbstractEngine#receivePackage(communication.AbstractPackage)
	 */
	public void receivePackage(AbstractPackage aPackage)
	{
		A.say("Master " + portal.getNodeId() + " received package " + aPackage.toString());
		
		if(aPackage instanceof SatelliteUpdatePackage)
		{
			processSatelliteUpdatePackage((SatelliteUpdatePackage) aPackage);
		}
	}
	
	private void processSatelliteUpdatePackage(SatelliteUpdatePackage p)
	{
		for(AbstractBlock block : p.getUpdatedBlocks())
		{
			Point location = block.getLocation();
			lastKnownAntId = p.getLastKnownAntId();
			AbstractBlock targetBlock = allBlocks[location.y][location.x];
			targetBlock.updateFromBlockAndBroadcast(block);
		}
	}
	
	public AbstractPackage packageForNewConnection(NodeId id)
	{
		SatelliteInitializationPackage p = new SatelliteInitializationPackage(portal.getNodeId(), allBlocks, lastKnownAntId);
		p.setIdForNewNode(id);
		return p;
	}

	private void generateAndDispatchUpdateForSatellites()
	{
		if(abstractBlockModQueue.isEmpty())
			return;
		
		MasterUpdatePackage p = new MasterUpdatePackage(portal.getNodeId(), abstractBlockModQueue, lastKnownAntId);
		abstractBlockModQueue.clear();
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			//Push the update queue every 5 seconds and then sleep.
			generateAndDispatchUpdateForSatellites();
			
			try 
			{
				Thread.sleep(5000);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public void addAbstractBlockToModQueue(AbstractBlock b)
	{
		// TODO Auto-generated method stub
		
	}
}
