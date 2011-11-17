package packages;

import java.io.Serializable;

import rpc.MessageType;

import communication.NodeId;

import utilities.A;

public class InvocationPackage extends AbstractPackage
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8520186103919851502L;
	private String methodName;
	private final Object [] arguments;
	private boolean isSynchronous;
	
	public InvocationPackage(NodeId nodeId, MessageId messageId, String methodName, MessageType type, Object [] arguments)
	{
		super(nodeId, messageId);
	
		if(arguments != null)
		{	
			for(Object o : arguments)
			{
				if(o == null)
				{
					A.error("A null argument was provided to the invocation package's arguments for a method call..");
					continue;
				}
				
				if(!(o instanceof Serializable))
					throw new IllegalArgumentException("All arguments to an invocation package must be serializable.  Object: " + o.getClass() + " is not.");
			}
		}
		
		isSynchronous = (type == MessageType.ASYNCHRONOUS) ? false : true;			
			
		this.methodName = methodName;
		this.arguments = arguments;
	}


	public MessageId getMessageId() 
	{
		return messageId;
	}


	public boolean isSynchronous() 
	{
		return isSynchronous;
	}

	
	public String getMethodName() 
	{
		return methodName;
	}

	public Object[] getArguments()
	{
		return arguments;
	}

	@Override
	public String toString()
	{
		String firstPart = "Synchronous: " + isSynchronous + " Method invocation package " + messageId + " from: " + sourceId + " for method: " + methodName + "\n with arguments: " + arguments;	
		
		
		
		for(Object o : arguments)
		{
			if(o instanceof Object[][])
			{
				Object[][] arr = (Object[][]) o;
				
				int sI = arr.length;
				int sJ = arr[0].length;
				
				for(int i=0; i< sI; i++)
				{
					firstPart += "\n";
					
					for(int j=0; j<sJ; j++)
					{
						firstPart += arr[i][j].toString();
					}
				}
			}
			else
			{
				firstPart += "\n" + o.toString();
			}
			
		}
		
		firstPart += "\n*********************";
		
		return firstPart;
	}

}
