package client;

import monitor.Graph;
import communication.Portal;
import communication.Recipient;

import constants.iConstants;

public class Client extends Portal
{	
	public Client(Recipient recipient, iConstants constants) 
	{
		super(recipient, constants);
		this.connectToDistributor();
	}
	
	public Graph getNetworkGraph()
	{
		return distributor.getObjectGraph();
	}
}
