package communication;

import distributor.NetworkResource.NetworkLocation;
import monitor.Graph;
import rpc.AnnotatedObject;

public interface iPortal 
{
	public AnnotatedObject makeNewConnectionToResource(String resourceName);
	public Graph getNetworkGraph();
	public NetworkLocation getNetworkLocation();
	public boolean isServer();
	public AnnotatedObject newConnectionToLocation(NetworkLocation location, String resourceName);
}
