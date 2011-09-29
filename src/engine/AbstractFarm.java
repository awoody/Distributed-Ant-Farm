package engine;

import communication.AbstractPackage;
import communication.Portal;

public abstract class AbstractFarm implements Portal
{
	private static Grid mainGrid;
	
	public AbstractFarm()
	{
		mainGrid = new Grid();
	}
	
	public AbstractFarm(Grid aGrid)
	{
		mainGrid = aGrid;
	}
	
	private Grid copyGrid()
	{
		return null;
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
