package packages;

import monitor.iNodeStatus;
import communication.NodeId;

public class ResourceUpdatePackage extends AbstractPackage
{

	private static final long serialVersionUID = -2824028634141843557L;
	private iNodeStatus nodeStatus;
	private boolean isReturnTrip;
	
	public ResourceUpdatePackage(NodeId id, MessageId messageId) 
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
	
	public iNodeStatus getNodeStatus()
	{
		return nodeStatus;
	}

	public void setNodeStatus(iNodeStatus nodeStatus)
	{
		this.nodeStatus = nodeStatus;
	}

	@Override
	public String toString() 
	{
		// TODO Auto-generated method stub
		return "Resource Identification Package";
	}
	
}
