package engine;

import java.awt.Point;

import communication.AbstractPackage;
import communication.Portal;

public abstract class AbstractEngine implements Portal
{
	protected static Grid mainGrid;
	
	public AbstractEngine()
	{
		mainGrid = new Grid();
	}
	
	public AbstractEngine(Grid aGrid)
	{
		mainGrid = aGrid;
	}
	
	private Grid copyGrid()
	{
		return null;
	}
	
	@Override
	public void addBlockSnapshot(Point blockLocation, BlockSnapshot snapshot)
	{
		
	}
	
	@Override
	public void dispatchPackage(AbstractPackage aPackage) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processClientPackage(AbstractPackage aPackage) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void processServerPackage(AbstractPackage aPackage) 
	{
		// TODO Auto-generated method stub
		
	}	
	
}
