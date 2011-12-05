package monitor;

import java.io.Serializable;

public interface iNodeStatus extends Serializable
{
	public double getAverageLatency();
	
	public int totalConnectedNodes();
	
	public double getPackagesPerSecond();
	
	public NodeType getNodeType();
}
