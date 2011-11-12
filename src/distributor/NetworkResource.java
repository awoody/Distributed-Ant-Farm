package distributor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import communication.NodeId;

public class NetworkResource
{
	private List<NetworkLocation> allLocationsThisResource;
	private int lastResourceAllocated;
	private String resourceName;
	
	public NetworkResource(String resourceName)
	{
		allLocationsThisResource = new ArrayList<NetworkLocation>();
		lastResourceAllocated = 0;
		this.resourceName = resourceName;
	}

	public void addLocation(int port, String address, NodeId node)
	{
		allLocationsThisResource.add(new NetworkLocation(port, address, node));
	}
	
	public NetworkLocation getNetworkLocation()
	{
		int totalLocations = allLocationsThisResource.size() - 1;
		
		if(lastResourceAllocated >= totalLocations)
			lastResourceAllocated = 0;
		
		return allLocationsThisResource.get(lastResourceAllocated++);
	}
	
	
	public String toString()
	{
		return "Network resource: " + resourceName + " with: " + allLocationsThisResource.size() +" locations on the network.";
	}
	
	
	public static class NetworkLocation implements Serializable
	{
		private static final long serialVersionUID = 3031728809494267272L;
		private int portNumber;
		private String address;
		private NodeId nodeId;
		
		public NetworkLocation(int portNumber, String address, NodeId nodeId)
		{
			super();
			this.portNumber = portNumber;
			this.address = address;
			this.nodeId = nodeId;
		}
		

		public NodeId getNodeId()
		{
			return nodeId;
		}
		
		
		public int getPortNumber()
		{
			return portNumber;
		}


		public String getAddress()
		{
			return address;
		}
	}	
}
