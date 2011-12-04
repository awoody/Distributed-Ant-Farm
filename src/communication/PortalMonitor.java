package communication;

import java.util.ArrayList;

import monitor.NodeType;
import monitor.iNodeStatus;

public class PortalMonitor
{
	private ArrayList<Long> recentLatencies = new ArrayList<Long>();
	private int packagesSent = 0;
	private int nodeType;
	private long trackingStartTime = System.currentTimeMillis();
	private int totalConnections = 0;
	
	public void trackLatency(long latency)
	{
		//A.error("Adding: " + latency);
		synchronized(recentLatencies)
		{
			recentLatencies.add(latency);
		}	
	}
	
	private void reset()
	{
		recentLatencies.clear();
		packagesSent = 0;
		trackingStartTime = System.currentTimeMillis();
	}
	
	public void packageSent()
	{
		packagesSent++;
	}
	
	
	public void setTotalConnections(int total)
	{
		totalConnections = total;
	}
	
	
	public void setNodeType(NodeType type)
	{
		if(type == NodeType.CLIENT)
			nodeType = 0;
		else if(type == NodeType.DISTRIBUTOR)
			nodeType = 1;
		else 
			nodeType = 2;
	}
	
	public iNodeStatus buildStatusAndReset()
	{
		double totalLatencyCandidates = (double) recentLatencies.size();
		
		long total = 0;
		//for(int i = 0; i < recentLatencies.size(); total += recentLatencies.get(i++));
		synchronized(recentLatencies)
		{
			for(Long l : recentLatencies) total+= l;
		}
					
		//A.error("Total is: " + total + " and size is: " + totalLatencyCandidates);
				
		double averageLatency = ((double) total) / totalLatencyCandidates;
		
		double totalSeconds = (System.currentTimeMillis() - trackingStartTime) / 1000;
		double packagesPerSecond = packagesSent / totalSeconds;
		
		PortalStatus pMoney = new PortalStatus(averageLatency, packagesPerSecond, nodeType, totalConnections);
		reset();
		
		return pMoney;
	}
}
