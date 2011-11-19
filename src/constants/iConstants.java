package constants;

import java.util.Map;

import communication.NodeId;

public interface iConstants
{
	public static NodeId defaultDistributorNodeId = new NodeId(0,0);
	
	/**
	 * Returns a map from the string resource name to all annotated
	 * objects you wish to use to facilitate network communication.
	 * If an object does not have an annotated object and will not 
	 * be used for network communication, you do not need to include 
	 * it here.  
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Class> getAnnotatedObjectsByString();
	
	/**
	 * Returns the default port that you wish your distributor to run
	 * on.
	 * 
	 * @return
	 */
	public int getDefaultDistributorPort();
	
	/**
	 * Returns the default host name for your distributor.
	 * @return
	 */
	public String getDefaultDistributorHostName();
	
	/**
	 * If this is true, any socket errors on the network will
	 * shutdown the entire network.  Otherwise, the network will recover from
	 * the error by simply disconnected the affected socket.
	 * 
	 * @return
	 */
	public boolean shutdownOnSocketError();
}
