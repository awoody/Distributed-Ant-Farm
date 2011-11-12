package distributor;

import communication.NodeId;

import distributor.NetworkResource.NetworkLocation;

public interface iDistributor 
{
	public NetworkLocation networkLocationForString(String objectName);
	
	public void registerNetworkResource(String resourceName, int port, String address, NodeId idOfResource);
	
	public String returnTestString();
}
