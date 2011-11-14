package api;

import client.Client;
import server.Server;
import utilities.A;
import communication.Recipient;
import communication.iPortal;

import constants.iConstants;

public class DistributedSystemFactory
{
	public iPortal newServer(int socket, Recipient r, iConstants constants)
	{
		return new Server(r, socket, constants);
	}
	
	public iPortal newClient(Recipient r, iConstants constants)
	{		
		return new Client(r, constants);
	}
}
