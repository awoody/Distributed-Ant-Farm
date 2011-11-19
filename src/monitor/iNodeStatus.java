package monitor;

public interface iNodeStatus
{
	public double getAverageLatency();
	
	public int totalConnectedNodes();
	
	public double getPackagesPerSecond();
}
