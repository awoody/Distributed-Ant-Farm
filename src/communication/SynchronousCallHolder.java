package communication;

import utilities.A;

import communication.Portal.NodeConnection;

public class SynchronousCallHolder
{
	private Thread heldThread;
	public static Thread socketListenerThread;
	private static long timeout = 5000;
	private Object returnValue;
	private AbstractPackage p;
	private NodeConnection nc;
	
	public static void setSocketListenerThread(Thread t)
	{
		socketListenerThread = t;
	}
	
//	public SynchronousCallHolder(Thread heldThread)
//	{
//		if(heldThread == socketListenerThread)
//		{
//			A.fatalError("Attempted to suspend the socket listener thread.  This is not permitted.");
//		}
//		
//		debug = false;
//		this.heldThread = heldThread;
//	}
	
	
	public SynchronousCallHolder(Thread heldThread, AbstractPackage p, NodeConnection c)
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
						
			//Thread not interrupted; we have waited too long; attempt recovery but only 
			//once
			
			if(recoveryAttempted) //recovery already attempted, crash the system.
			{
				A.fatalError("A thread was not resumed after too long.");
			}
			
			recoveryAttempted = true;
			A.error("Thread deadlock detected.");
			A.error("The package was: " + p.toString());
			A.error("Attempting recovery by resending package.");
			nc.writeToOutputStream(p);
		}
	}
	
	public void continueThread()
	{
		heldThread.interrupt(); //Should break it out of sleeping if it is.	
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
