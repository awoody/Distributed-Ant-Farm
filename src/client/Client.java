package client;
import java.net.Socket;
import java.util.List;
import utilities.A;

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
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}		
	}

	@Override
	public List<NodeId> getAllConnectedNodes() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispatchPackage(AbstractPackage aPackage, List<NodeId> recipients) 
	{
		if(recipients != null)
			throw new IllegalArgumentException("Recipients should always be null on calls to dispatch package from the client, since the only recipient is the current satellite.");
		
		A.say("Client " + nodeId + " sending package " + aPackage.toString());
		connection.send(aPackage);
	}

	@Override
	public void dispatchDirectlyToMaster(AbstractPackage aPackage) 
	{
		return;
	}	
}
