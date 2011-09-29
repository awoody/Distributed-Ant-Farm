package client;

import engine.BlockSnapshot;

public class Ant
{
	private BlockSnapshot[][]currentLocation;

	public Ant()
	{
		
	}
	
	public BlockSnapshot[][] getCurrentLocation() 
	{
		return currentLocation;
	}

	public void setCurrentLocation(BlockSnapshot[][] currentLocation) 
	{
		this.currentLocation = currentLocation;
	}
	
	public void sendUpdate()
	{
		
	}
}
