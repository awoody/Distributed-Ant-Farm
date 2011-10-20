package satellite;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.*;

import communication.AbstractPackage;
import communication.AbstractPortal;
import communication.NodeId;
import utilities.A;

//master server
public class SatelliteServer extends AbstractPortal
{
	private ServerSocket serverSocket;
	protected Map<NodeId, NodeConnection> allConnectedNodes;
	private final boolean run;
	private int nextNodeId;
	private NodeConnection connectionToMaster;
			
	public SatelliteServer(int portId)
	{	
		run = true;
		nextNodeId = 0;
		try 
		{
			serverSocket = new ServerSocket(portId);
			allConnectedNodes = new HashMap<NodeId, NodeConnection>();
			
			Socket socket = new Socket("localhost", 11001);
			connectionToMaster = new NodeConnection(socket);
			Thread connectionThread = new Thread(connectionToMaster);
			connectionThread.start();
			//Connecting to the master will cause the nodeId to be set by the
			//first incoming package.
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			A.say("Unable to create the server socket.");
		}
	}
	
	public void run()
	{
		A.say("Server is running.");
		while (run)
		{
			Socket clientSocket;
			try 
			{
				clientSocket = serverSocket.accept();
				//When a new socket is created, assign it a node id, 
				//add it to the map of all existing connections, 
				//start a new thread for it and then send it
				//the recipient's implementation of an initialization
				//package.
				A.say("Processing a new connection to satellite " + nodeId);
				NodeConnection newConnection = new NodeConnection(clientSocket);
				Thread newClientThread = new Thread(newConnection);	
				NodeId newNode = generateNewNodeId();
				newClientThread.start();
				newConnection.send(recipient.packageForNewConnection(newNode));
				allConnectedNodes.put(newNode,newConnection);
				A.say("A new connection was accepted by the satellite, and assigned the nodeId " + newNode + " and connection " + newConnection);
			}
			catch (IOException e) 
			{
				A.say("Failed to make a connection to a client");
			}
		}

	}
	
	public synchronized NodeId generateNewNodeId()
	{
		return new NodeId(++nextNodeId, nodeId.getId()); //This must be an incoming
		//client connection, so parent it to this satellite.
	}
	
	/* (non-Javadoc)
	 * @see communication.Portal#getAllConnectedNodes()
	 */
	public List<NodeId> getAllConnectedNodes()
	{
		return null;
	}
	
	@Override
	public void dispatchPackage(AbstractPackage aPackage, List<NodeId> recipients)
	{		
		for(NodeId node : recipients)
		{
			A.say("Satellite " + nodeId + " sending package " + aPackage.toString());
			
			NodeConnection c = allConnectedNodes.get(node);
			
			if(c != null)
			{	
				c.send(aPackage);
			}
		}
	}

	@Override
	public void dispatchDirectlyToMaster(AbstractPackage aPackage) 
	{
		A.say("Satellite dispatching a message to the master.");
		connectionToMaster.send(aPackage);	
	}
}
