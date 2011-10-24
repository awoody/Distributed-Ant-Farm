package antImplementation;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import utilities.A;
import communication.AbstractPackage;
import communication.NodeId;
import communication.Portal;
import engine.AbstractSatelliteEngine;

/**
 * This is the ant-farm implementation of the AbstractSatelliteEngine object.
 * This is where the general distributed server ends and the ant farm 
 * begins.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class SatelliteAntFarm extends AbstractSatelliteEngine implements ModificationQueue
{
	private int antId = 0;
	private AbstractBlock[][] allBlocks;
	private List<AbstractBlock> abstractBlockModQueue;
	
	public SatelliteAntFarm(Portal aPortal) 
	{
		super(aPortal);
		// TODO Auto-generated constructor stub
		
		abstractBlockModQueue = new ArrayList<AbstractBlock>();
		AbstractBlock.setModQueue(this);
		
	}
	
	public void receivePackage(AbstractPackage p)
	{
		A.say("SatelliteAntFarm " + portal.getNodeId() + " received package " + p.toString());
		
		if(p instanceof AntClientToSatellitePackage)
		{
			processClientToSatellitePackage((AntClientToSatellitePackage) p);
		}
		else
			processMasterToSatellitePackage((AntMasterToSatellitePackage) p);
	}
	
	private void processClientToSatellitePackage(AntClientToSatellitePackage p)
	{
		if(p instanceof AntMovementPackage)
		{
			handleMovementPackage((AntMovementPackage) p);
		}
	}
		
	private void handleMovementPackage(AntMovementPackage p)
	{
		AntMovementPackage movementPackage = (AntMovementPackage) p;
		Point newLocation = movementPackage.getDesiredMoveToPoint();
		
		//Ignore if the little shit is trying to move out of bounds.
		if(newLocation.x >= allBlocks[0].length || newLocation.x < 0)
			return;
		
		if(newLocation.y >= allBlocks.length || newLocation.y < 0)
			return;
		
		int antId = p.getAntId();
		Point oldLocation = p.getInitialPoint();
		
		//Remove the ant from the old block.
		AbstractBlock oldBlock = allBlocks[oldLocation.y][oldLocation.x];
		oldBlock.removeAnt(antId);
		
		//Add it to the new block
		AbstractBlock newBlock = allBlocks[newLocation.y][newLocation.x];
		newBlock.addAnt(antId);
		
		//Fetch the new segment for the ant's perspective
		AbstractBlock[][] newSegement = getSegmentForPointWithRadius(newLocation, 3);
		
		//Dispatch the package back to the ant.
		AntUpdatePackage movementUpdate = new AntUpdatePackage(portal.getNodeId(), newSegement, newLocation, antId);
		portal.dispatchPackage(movementUpdate, setFromSingleton(p.nodeId()));
	}
	
	private void processMasterToSatellitePackage(AntMasterToSatellitePackage p)
	{
		if(p instanceof SatelliteInitializationPackage)
			this.allBlocks = ((SatelliteInitializationPackage) p).getAllBlocks();
		
		if(p instanceof MasterUpdatePackage)
			processMasterUpdatePackage((MasterUpdatePackage) p);
	}
	
	private void processMasterUpdatePackage(MasterUpdatePackage p)
	{
		for(AbstractBlock block : p.getUpdatedBlocks())
		{
			Point location = block.getLocation();
			
			AbstractBlock targetBlock = allBlocks[location.y][location.x];
			targetBlock.updateFromBlock(block);
		}
		
		antId = p.getLastKnownAntId();
	}
	
	public AbstractBlock[][] getSegmentForPointWithRadius(Point p, int radius)
	{
		int x = p.x;
		int y = p.y;
		int breadthX = radius+1;
		int breadthY = radius+1;
		
		x-=radius;
		y-=radius;
		
		if(x < 0)
		{
			breadthX -= x; 
			x = 0;
		}	
		if(y < 0)
		{
			breadthY -= y;
			y = 0;
		}
		
		if((x + breadthX) >= allBlocks[0].length)
		{
			breadthX -= (x - allBlocks[0].length);
		}	
		if(y >= allBlocks.length)
		{
			breadthY -= (y - allBlocks.length);
		}
		
		AbstractBlock[][] returnBlocks = new AbstractBlock[breadthY][breadthX];
		
		for(int i = 0; i < breadthY; i++)
		{
			for(int j = 0; j < breadthX; j++)
			{
				returnBlocks[i][j] = allBlocks[y + i][x + j];
			}
		}
		
		return returnBlocks;
	}
	
	public AbstractPackage packageForNewConnection(NodeId id)
	{
		Point defaultPoint = new Point(allBlocks.length / 2, allBlocks.length / 2);
		AbstractBlock startingBlock = allBlocks[defaultPoint.y][defaultPoint.x];
		startingBlock.addAnt(++antId);
		AbstractBlock[][] startingSegment = getSegmentForPointWithRadius(defaultPoint, 3);
		AntInitializationPackage newAnt = new AntInitializationPackage(portal.getNodeId(), antId, startingSegment, defaultPoint);
		newAnt.setIdForNewNode(id);
		
		return newAnt;
	}

	public void generateAndDispatchUpdateToMaster()
	{
		if(abstractBlockModQueue.isEmpty())
		{	
			A.say("Satellite has no updates to push to master.");
			return;
		}
		
		synchronized(abstractBlockModQueue)
		{	
			SatelliteUpdatePackage updatePackage = new SatelliteUpdatePackage(portal.getNodeId(), abstractBlockModQueue, antId);
			portal.dispatchDirectlyToMaster(updatePackage);
			abstractBlockModQueue = new ArrayList<AbstractBlock>();
		}
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			//Push the update queue every 5 seconds and then sleep.
			A.say("Satellite pushing updates to master server.");
			generateAndDispatchUpdateToMaster();
			
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
		synchronized(abstractBlockModQueue)
		{
			A.say("Satellite farm adding: " +  b  + " to mod queue");
			abstractBlockModQueue.add(b);
		}
	}

}
