package api;

import client.Client;
import server.Server;
import utilities.A;
import communication.Recipient;
import communication.iPortal;

import constants.iConstants;
import distributor.Distributor;

public class DistributedSystemFactory
{	
	public static iPortal newServer(int socket, Recipient r, iConstants constants)
	{
		return new Server(r, socket, constants);
	}
	
	public static iPortal newClient(Recipient r, iConstants constants)
	{		
		return new Client(r, constants);
	}
	
	public static void startDistributor(iConstants constants)
	{
		new Distributor(constants);
	}
}
