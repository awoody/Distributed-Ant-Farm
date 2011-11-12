package communication;

public class ResponsePackage extends AbstractPackage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7622809068347273749L;
	private Object returnValue;
	
	public ResponsePackage(NodeId id, MessageId messageId, Object returnValue)
	{
		super(id, messageId);
		// TODO Auto-generated constructor stub
		this.returnValue = returnValue;
	}

	
	public Object getReturnValue()
	{
		return returnValue;
	}
	
	
	@Override
	public String toString() 
	{
		// TODO Auto-generated method stub
		return "Response package with object: " + returnValue + " for message: " + messageId;
	}
}
