package communication;

public class SynchronousCallHolder
{
	private final Thread heldThread;
	private Object returnValue;
	
	public SynchronousCallHolder(Thread heldThread)
	{
		this.heldThread = heldThread;
	}
	
	@SuppressWarnings("deprecation")
	public void holdThread()
	{
		heldThread.suspend();
	}
	
	@SuppressWarnings("deprecation")
	public void continueThread()
	{
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
