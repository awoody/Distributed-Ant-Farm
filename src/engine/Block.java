package engine;

import java.io.Serializable;

public class Block 
{
	public boolean isTwiddled;
	
	public BlockSnapshot getSnapshot()
	{
		return new BlockSnapshot(this);
	}
}
