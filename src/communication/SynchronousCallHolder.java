package communication;

import utilities.A;

public class SynchronousCallHolder
{
	private final Thread heldThread;
	public static Thread socketListenerThread;
	private Object returnValue;
	
	public static void setSocketListenerThread(Thread t)
	{
		socketListenerThread = t;
	}
	
	public SynchronousCallHolder(Thread heldThread)
	{
		if(heldThread == socketListenerThread)
		{
			A.fatalError("Attempted to suspend the socket listener thread.  This is not permitted.");
		}
		
		this.heldThread = heldThread;
	}
	
	@SuppressWarnings("deprecation")
	public void holdThread()
	{
		//System.out.println("Holding Thread: " + heldThread);
		heldThread.suspend();
	}
	
	@SuppressWarnings("deprecation")
	public void continueThread()
	{
		//System.out.println("Resuming Thread: " + heldThread);
		heldThread.resume();
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
