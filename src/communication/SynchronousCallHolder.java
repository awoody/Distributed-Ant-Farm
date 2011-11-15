package communication;

import communication.Portal.NodeConnection;

import utilities.A;

public class SynchronousCallHolder
{
	private Thread heldThread;
	public static Thread socketListenerThread;
	private static long timeout = 5000;
	private static long sleepTime = 100;
	private Object returnValue;
	private boolean isSuspended;
	private boolean debug;
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
	
		debug = true;
		this.p = p;
		this.nc = c;
		this.heldThread = heldThread;
	}
	
	
	@SuppressWarnings("deprecation")
	public void holdThread()
	{
		//System.out.println("Holding Thread: " + heldThread);
		isSuspended = true;
		heldThread.suspend();
		return;
		
//		long currentTime = System.currentTimeMillis();
//		boolean recoveryAttempted = false;
//		
//		while(true)
//		{
//			try 
//			{
//				Thread.sleep(sleepTime);
//			}
//			catch (InterruptedException e) 
//			{
//				if(heldThread == null)
//					break;
//			}
//					
//			if(!isSuspended)
//				break;
//			
//			
//			if(recoveryAttempted)
//			{
//				A.fatalError("A thread was not resumed after too long.");
//			}
//			
//			long latestTime = System.currentTimeMillis();
//			
//			if(latestTime - currentTime > timeout)
//			{
//				recoveryAttempted = true;
//				A.error("Thread deadlock detected.");
//				A.error("The package was: " + p.toString());
//				A.error("Attempting recovery by resending package.");
//				nc.writeToOutputStream(p);
//			}
//		}
	}
	
	@SuppressWarnings("deprecation")
	public void continueThread()
	{
		//System.out.println("Resuming Thread: " + heldThread);
		isSuspended = false;
		heldThread.resume(); //Should break it out of sleeping if it is.
		//heldThread = null;
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
