package constants;

import java.io.Serializable;
import java.util.Map;

import communication.NodeId;

public interface iConstants extends Serializable
{
	@SuppressWarnings("rawtypes")
	public Map<String, Class> getAnnotatedObjectsByString();
	public int getDefaultDistributorPort();
	public String getDefaultDistributorHostName();
	public NodeId getDistributorNodeId();
}
