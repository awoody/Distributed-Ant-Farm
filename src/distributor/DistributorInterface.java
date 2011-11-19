package distributor;

import monitor.Graph;
import communication.NodeId;
import distributor.NetworkResource.NetworkLocation;

import rpc.AnnotatedObject;
import rpc.Asynchronous;
import rpc.Synchronous;

public class DistributorInterface extends AnnotatedObject implements iDistributor
{		
	@Synchronous
	public NetworkLocation connectionToResourceForNode(NodeId requestingNode, String objectName)
	{
		return null;
	}


	@Synchronous
	public void registerNetworkResource(String resourceName, int port,
			String address, NodeId idOfResource) {
		// TODO Auto-generated method stub
		
	}


	@Synchronous
	public Graph getObjectGraph()
	{
		return null;
		// TODO Auto-generated method stub
		
	}


	@Asynchronous
	public void addEdgeFromAtoB(NodeId start, NodeId end)
	{
		// TODO Auto-generated method stub
		
	}


	@Asynchronous
	public void removeEdgeFromAtoB(NodeId start, NodeId end)
	{
		// TODO Auto-generated method stub
		
	}
}
