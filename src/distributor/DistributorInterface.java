package distributor;

import communication.NodeId;
import communication.NodeType;
import distributor.NetworkResource.NetworkLocation;

import rpc.AnnotatedObject;
import rpc.Synchronous;

public class DistributorInterface extends AnnotatedObject implements iDistributor
{	
	@Synchronous
	public NodeId registerNewObject(String objectName, NodeType nodeType)
	{
		return null;
	}
	
	
	@Synchronous
	public NetworkResource obtainObjectFromString(String objectName)
	{
		return null;
	}


	@Synchronous
	public String returnTestString() 
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Synchronous
	public NetworkLocation networkLocationForString(String objectName) 
	{
		// TODO Auto-generated method stub
		return null;
	}


	@Synchronous
	public void registerNetworkResource(String resourceName, int port,
			String address, NodeId idOfResource) {
		// TODO Auto-generated method stub
		
	}
}
