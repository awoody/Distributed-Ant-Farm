package communication;

import distributor.NetworkResource.NetworkLocation;
import monitor.Graph;
import rpc.AnnotatedObject;

public interface iPortal 
{
	/**
	 * This will return an annotated object corresponding to the provided
	 * resourceName, which may be thought of as a connection to a resource
	 * of that type on the network.  You will need to cast it appropriately
	 * since you'll have the context to know which specific AnnotatedObject
	 * is returned by this call.
	 * 
	 * @param resourceName
	 * @return An annotated object corresponding to that resource.  This object
	 * 			is guaranteed to be the same as the object the resourceName string
	 * 			is mapped to in the constants file you have provided.
	 */
	public AnnotatedObject makeNewConnectionToResource(String resourceName);
	
	/**
	 * Calling this method will cause the network to query all nodes in the network
	 * for their current status, and return a graph of all nodes to the caller.  This
	 * graph is not guaranteed to be 100% current, but it is guaranteed to send an 
	 * asynchronous message to all nodes in the network.  Therefore, it will cause all
	 * nodes to send an updated status to the distributor, but not all of those messages
	 * may have returned by the time you get a copy of the graph.  Over time however, the
	 * network will stabilize and the image will become more current as the network changes
	 * less.
	 * 
	 * @return A Graph object representing the current network.
	 */
	public Graph getNetworkGraph();
	
	/**
	 * Returns the network location of this portal.  If this portal is a client, an exception
	 * is thrown because clients do not have network locations since they cannot accept incoming
	 * connections.  This is meant to be used with the newConnectionToLocation() method.
	 * 
	 * @return The NetworkLocation object repesenting this node's location.
	 */
	public NetworkLocation getNetworkLocation();
	
	/**
	 * Returns true if this location can accept incoming connections from other nodes.  A distributor
	 * is part of this list.
	 * 
	 * @return True if this portal can accept connections, false otherwise.
	 */
	public boolean isServer();
	
	/**
	 * This method is a more direct version of MakeNewConnection().  With this method, in addition
	 * to the resource name, you specify the exact location on the network of the node you wish to
	 * connect to.  This gives you more fine-grained control over your network configuration.
	 * 
	 * @param location - The location to connect to.
	 * @param resourceName - The name of the resource at that location.
	 * @return An annotated object corresponding to that resource.  This object
	 * 			is guaranteed to be the same as the object the resourceName string
	 * 			is mapped to in the constants file you have provided.
	 */
	public AnnotatedObject newConnectionToLocation(NetworkLocation location, String resourceName);
	
	/**
	 * Closes the connection between the portal and the indicated annotated object.
	 * 
	 * @param objToClose - The object you wish to disconnect from.
	 */
	public void closeConnection(AnnotatedObject objToClose);
	
	/**
	 * Completely kills this portal and all of its connections, removing it from the network entirely.
	 */
	public void shutdown();
}
