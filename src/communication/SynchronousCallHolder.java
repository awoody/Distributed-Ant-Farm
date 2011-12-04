package communication;

import packages.AbstractPackage;
import utilities.A;

import communication.Portal.NodeConnection;

public class SynchronousCallHolder
{
	private Thread heldThread;
	public static Thread socketListenerThread;
	private static long timeout = 10000;
	private long holdStartTime = 0;
	private Object returnValue;
	private AbstractPackage p;
	private NodeConnection nc;
	private PortalMonitor monitor;
	
	public static void setSocketListenerThread(Thread t)
	{
		socketListenerThread = t;
	}
	
	
	public SynchronousCallHolder(Thread heldThread, AbstractPackage p, NodeConnection c, PortalMonitor monitor)
	{	
		if(heldThread == socketListenerThread)
		{
			A.fatalError("Attempted to suspend the socket listener thread.  This is not permitted.");
		}
	
		this.p = p;
		this.nc = c;
		this.heldThread = heldThread;
	}
	
	
	public void holdThread()
	{	
		boolean recoveryAttempted = false;
		holdStartTime = System.currentTimeMillis();
		
		//The value has already been set before the call
		//to holdThread even occurred; don't even enter
		//this loop.
		if(returnValue != null)
			return;
		
		while(true)
		{
			try 
			{
				Thread.sleep(timeout);
			}
			catch (InterruptedException e) 
			{
				break; //thread was interrupted by continueThread(), this is a good thing.
			}
						
			if(returnValue != null)
				return;
			
			//Thread not interrupted; we have waited too long; attempt recovery but only 
			//once
			
			if(recoveryAttempted) //recovery already attempted, crash the system.
			{
				A.fatalError("A thread was not resumed after too long.");
			}
			
			recoveryAttempted = true;
			A.error("Thread timeout exceeded.");
			A.error("The package was: " + p.toString());
			A.error("Attempting recovery by resending package.");
			nc.writeToOutputStream(p);
		}
	}
	
	public void continueThread()
	{
		heldThread.interrupt(); //Should break it out of sleeping if it is.	
		long timeHeld = System.currentTimeMillis() - holdStartTime;
		monitor.trackLatency(timeHeld);
	}

	public Object getReturnValue()
	{
		return returnValue;
	}

	public void setReturnValue(Object returnValue) 
	{
		this.returnValue = returnValue;
	}	
}
