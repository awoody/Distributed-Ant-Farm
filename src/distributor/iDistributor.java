package distributor;

import monitor.Graph;
import communication.NodeId;

import distributor.NetworkResource.NetworkLocation;

public interface iDistributor 
{
	public NetworkLocation connectionToResourceForNode(NodeId requestingNode, String objectName);
	
	public void registerNetworkResource(String resourceName, int port, String address, NodeId idOfResource);
	
	/**
	 * Returns the most current copy of the network's object graph complete
	 * with update information for all of the nodes in the graph.
	 * @return 
	 */
	public Graph getObjectGraph();
	
	public void addEdgeFromAtoB(NodeId start, NodeId end);
	
	public void removeEdgeFromAtoB(NodeId start, NodeId end);
}
