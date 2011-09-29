package engine;

import java.io.Serializable;

public class GridSnapshot implements Serializable
{
	private static final long serialVersionUID = 9193241912543211414L;
	private BlockSnapshot[][] allBlockSnapshots;
	
	public GridSnapshot(Grid g, Block[][] allBlocks)
	{
		//etc methods, and then:
		int gridSize = allBlocks.length;
		allBlockSnapshots = new BlockSnapshot[gridSize][gridSize];
		
		for(int i = 0; i < gridSize; i++)
			for(int j=0; j < gridSize; j++)
				allBlockSnapshots[i][j] = allBlocks[i][j].getSnapshot();
		
		
	}
}
