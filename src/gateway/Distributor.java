package gateway;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import utilities.A;

import communication.AbstractPackage;
import communication.AbstractPortal;
import communication.DistributorPackage;
import communication.NodeId;
import communication.AbstractPortal.NodeConnection;


public class Distributor extends AbstractPortal
{
	private Queue<Integer> allSatellites;
	private ServerSocket serverSocket;
	int totalSatellites = 4;
	private boolean isRunning;
	
	public Distributor()
	{
		try {
			serverSocket = new ServerSocket(11000);
			allSatellites = new LinkedList<Integer>();
			nodeId = new NodeId(-1, -1);
			isRunning = true;
			//Establish a node connection with all of the satellite servers.
			for(int i = 1; i <= totalSatellites; i++)
			{
				int port = 11001 + i;
				allSatellites.offer(port);
			}
			
			A.say("Distributor is running.");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

	}
	
	public void run() 
	{		
		Socket clientSocket;
		try 
		{
			while (isRunning)
			{
				clientSocket = serverSocket.accept();
				// When something conencts to the distributor, create a
				// distributor package with the port of the appropriate
				// satellite server for it to connect to.
				A.say("Handling a connection to the distribution server.");
				NodeConnection newConnection = new NodeConnection(clientSocket);
				Thread newClientThread = new Thread(newConnection);
				newClientThread.start();
				int nextSatellitePort = allSatellites.remove();
				allSatellites.offer(nextSatellitePort);
				DistributorPackage dp = new DistributorPackage(null, nextSatellitePort);
				newConnection.send(dp); // Send the distributor package directly
										// to the node.
				A.say("Distributor distributed new client to satellite at: "
						+ nextSatellitePort);
			}
		}
		catch (IOException e) 
		{
			A.say("Failed to make a connection to a client");
		}
		

	}

	@Override
	public Set<NodeId> getAllConnectedNodes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void dispatchPackage(AbstractPackage aPackage, Set<NodeId> recipients) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispatchDirectlyToMaster(AbstractPackage aPackage)
	{
		// TODO Auto-generated method stub
		
	}

}
