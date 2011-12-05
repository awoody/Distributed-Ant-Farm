package client;

import monitor.Graph;
import monitor.NodeType;
import utilities.A;

import communication.Portal;
import communication.Recipient;

import constants.iConstants;
import distributor.NetworkResource.NetworkLocation;

public class Client extends Portal
{	
	public Client(Recipient recipient, iConstants constants) 
	{
		super(recipient, constants);
		this.connectToDistributor();
		monitor.setNodeType(getNodeType());
	}
	
	public Graph getNetworkGraph()
	{
		return distributor.getObjectGraph();
	}

	@Override
	public NetworkLocation getNetworkLocation()
	{
		A.fatalError("Do not call get network location on the client; it is not a server and as such has no location.");
		return null;
	}

	@Override
	public boolean isServer()
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public NodeType getNodeType()
	{
		// TODO Auto-generated method stub
		return NodeType.CLIENT;
	}
}
