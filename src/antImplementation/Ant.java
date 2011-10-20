package antImplementation;

import java.awt.Point;
import java.util.Random;

import utilities.A;
import communication.AbstractPackage;
import communication.Portal;
import engine.AbstractClientEngine;

/**
 * This is the ant-farm implementation of the AbstractClientEngine object.
 * This is where the general distributed server ends and the ant farm 
 * begins.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class Ant extends AbstractClientEngine
{
	private boolean isRunning;
	private boolean dataCurrent;
	private Point currentLocation;
	private Random randomGenerator;
	private int antId;
	private AbstractBlock[][] blockSegment;

	public Ant(Portal aPortal) 
	{
		super(aPortal);
		// TODO Auto-generated constructor stub
		isRunning = true;
		randomGenerator = new Random(System.currentTimeMillis());
		dataCurrent = false;
		
		antId = -1;
	}
	
	@Override
	public void receivePackage(AbstractPackage aPackage)
	{
		A.say("Ant " + portal.getNodeId() + " received package " + aPackage.toString());
		
		dataCurrent = false;
		
		//Process update information.
		
		if(aPackage instanceof AntUpdatePackage)
		{
			AntUpdatePackage p = (AntUpdatePackage) aPackage;
			this.currentLocation = p.getNewLocation();
			this.blockSegment = p.getUpdatedBlocks();
		}
		
		if(aPackage instanceof AntInitializationPackage)
		{
			AntInitializationPackage p = (AntInitializationPackage) aPackage;
			this.antId = p.getAntId();
			this.blockSegment = p.getSegment();
			this.currentLocation = p.getStartingLocation();
		}
		
		//As long as the antId is there, I can only assume that the last
		//update made the data current and the run loop can continue.
		if(antId >= 0)
			dataCurrent = true;
		
		return;
	}

	@Override
	public void run()
	{
		while(true)
		{
			if(currentLocation != null)
			{
				
				//Allows ants to make random move descisions as proof of concept.
				int x = currentLocation.x;
				int y = currentLocation.y;
				
				int nextX = randomGenerator.nextInt(2);
				int nextY = randomGenerator.nextInt(2);
				
				x+=nextX; 
				y+=nextY;
				
				AntMovementPackage moveMe = new AntMovementPackage(portal.getNodeId(), antId, currentLocation, new Point(x, y));
				portal.dispatchPackage(moveMe, null);
				
				try
				{
					Thread.sleep(10000);
				}
				catch (InterruptedException e) 
				{
					A.say("Client thread was interrupted during sleep call");
					e.printStackTrace();
				}
			}
			
		}
	}

}
