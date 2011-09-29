package engine;

import java.awt.Point;

import communication.AbstractPackage;
import communication.Portal;

import utilities.Constants;

public class Grid 
{
	private final Block[][] allBlocks;
	private static Portal portal;
	private Point antHole;
	
	public Point getAntHoleLocation()
	{
		return antHole;
	}
	
	public Grid()
	{
		super();
		
		int gridSize = Constants.GRID_SIZE;		
		allBlocks = new Block[gridSize][gridSize];
		
		//Build the block array stored in the grid.
		for(int i = 0; i < gridSize; i++)
			for(int j=0; j < gridSize; j++)
				allBlocks[i][j] = new Block();
		
		int holeLocation = gridSize/2;
		antHole = new Point(holeLocation, holeLocation);
	}
	
	public GridSnapshot getDeepCopy()
	{
		return new GridSnapshot(this, allBlocks);
	}
	
	public BlockSnapshot[][] getSnapshotForAnt(Point antLocation, int visionRadius)
	{
		int startX = antLocation.x - visionRadius;
		int startY = antLocation.y - visionRadius;
		
		int lengthX = (visionRadius*2) + 1;
		int lengthY = (visionRadius*2) + 1;
		
		//Normalize if the ant is near any edge.		
		if(startX < 0)
		{
			lengthX -= startX;
			startX = 0;
		}	
		if(startY < 0)
		{
			lengthY -= startY;
			startY = 0;
		}
		
		//Local alias
		int gridSize = Constants.GRID_SIZE;		
		if(startX > gridSize)
		{
			lengthX -= (startX - gridSize);
			startX = gridSize;
		}		
		if(startY > gridSize)
		{
			lengthY -= (startY - gridSize);
			startY = gridSize;
		}
		
		//Now build the grid of snapshots for the ant.
		BlockSnapshot[][] snapshot = new BlockSnapshot[lengthX][lengthY];
		
		for(int i=0; i < lengthY; i++)
			for(int j=0; j < lengthX; i++)
				snapshot[j][i] = allBlocks[j][i].getSnapshot();
		
		return snapshot;
	}
}
