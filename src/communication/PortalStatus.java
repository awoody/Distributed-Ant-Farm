package communication;

import monitor.NodeType;
import monitor.iNodeStatus;

public class PortalStatus implements iNodeStatus
{
	private static final long serialVersionUID = -4938210343557472037L;
	private final double averageLatency;
	private final double packagesPerSecond;
	private final int nodeType;
	private final int totalConnections;

	public PortalStatus(double latency, double packagesPerSecond, int type,
			int totalConnections)
	{
		this.averageLatency = latency;
		this.packagesPerSecond = packagesPerSecond;

		this.nodeType = type;

		this.totalConnections = totalConnections;
	}

	@Override
	public double getAverageLatency()
	{
		// TODO Auto-generated method stub
		return averageLatency;
	}

	@Override
	public int totalConnectedNodes()
	{
		// TODO Auto-generated method stub
		return totalConnections;
	}

	@Override
	public double getPackagesPerSecond()
	{
		// TODO Auto-generated method stub
		return packagesPerSecond;
	}

	@Override
	public NodeType getNodeType()
	{
		if (nodeType == 0)
			return NodeType.CLIENT;
		else if (nodeType == 1)
			return NodeType.DISTRIBUTOR;
		else
			return NodeType.SERVER;
	}
}