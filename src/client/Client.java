package client;
import java.net.Socket;
import java.util.Set;
import java.io.*;

import utilities.A;

import antImplementation.Ant;
import antImplementation.AntDummyPackage;

import communication.AbstractPackage;
import communication.AbstractPortal;
import communication.NodeId;

public class Client extends AbstractPortal
{
	private NodeConnection connection;
	
	public void run()
	{		
		try 
		{			
			Socket socket = new Socket("localhost", 11000);
			connection = new NodeConnection(socket);
			Thread connectionThread = new Thread(connection);
			connectionThread.start();
			
			A.say("Client connected to server.");
			while(true)
			{
				AntDummyPackage p = new AntDummyPackage(nodeId);
				p.setDummyValue("Leaving the client!");
				dispatchPackage(p, null);
				Thread.sleep(10000);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}

	@Override
	public Set<NodeId> getAllConnectedNodes() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispatchPackage(AbstractPackage aPackage, Set<NodeId> recipients) 
	{
		connection.send(aPackage);
	}

	@Override
	public void dispatchDirectlyToMaster(AbstractPackage aPackage) 
	{
		//Not allowed by client.
	}	
}
