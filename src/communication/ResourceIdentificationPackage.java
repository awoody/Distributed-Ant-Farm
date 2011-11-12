package communication;

public class ResourceIdentificationPackage extends AbstractPackage
{
	private static final long serialVersionUID = -2824028634141843557L;
	private NodeId resourceNodeId;
	private String resourceObjectName;
	private boolean isReturnTrip;
	
	public ResourceIdentificationPackage(NodeId id, MessageId messageId) 
	{
		super(id, messageId);
		isReturnTrip = false;
		// TODO Auto-generated constructor stub
	}

	public void flip()
	{
		isReturnTrip = !isReturnTrip;
	}
	
	
	public boolean isReturningToSender()
	{
		return isReturnTrip;
	}
	
	
	public NodeId getResourceNodeId()
	{
		return resourceNodeId;
	}

	public void setResourceNodeId(NodeId resourceNodeId) 
	{
		this.resourceNodeId = resourceNodeId;
	}

	public String getResourceObjectName() 
	{
		return resourceObjectName;
	}

	public void setResourceObjectName(String resourceObjectName) 
	{
		this.resourceObjectName = resourceObjectName;
	}

	@Override
	public String toString() 
	{
		// TODO Auto-generated method stub
		return "Resource Identification Package";
	}	
}
