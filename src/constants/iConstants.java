package constants;

import java.util.Map;

import communication.NodeId;

public interface iConstants
{
	@SuppressWarnings("rawtypes")
	public Map<String, Class> getAnnotatedObjectsByString();
	public int getDefaultDistributorPort();
	public String getDefaultDistributorHostName();
	public NodeId getDistributorNodeId();
}
