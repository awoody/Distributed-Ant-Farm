package master;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.IOException;
import java.util.*;

import communication.AbstractPackage;
import communication.AbstractPortal;
import communication.NodeId;
import utilities.A;

//master server
public class Master extends AbstractPortal
{
	private ServerSocket serverSocket;
	protected Map<NodeId, NodeConnection> allConnectedNodes;
	private final boolean run;
	private static int nextNodeId;
			
	public Master()
	{	
		run = true;
		nextNodeId = 0;
		try 
		{
			serverSocket = new ServerSocket(11001);
			allConnectedNodes = new HashMap<NodeId, NodeConnection>();
			nodeId = new NodeId(nextNodeId, 0);
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
				A.say("Processing a new connection to the master server.");
				NodeConnection newConnection = new NodeConnection(clientSocket);
				Thread newClientThread = new Thread(newConnection);	
				NodeId newNode = generateNewNodeId();
				newClientThread.start();
				newConnection.send(recipient.packageForNewConnection(newNode));
				allConnectedNodes.put(newNode,newConnection);
				A.say("A new connection was accepted by the master, and assigned the nodeId " + newNode + " and connection " + newConnection);
			}
			catch (IOException e) 
			{
				A.say("Failed to make a connection to a client");
			}
		}

	}
	
	public synchronized NodeId generateNewNodeId()
	{
		return new NodeId(++nextNodeId, nodeId.getId()); //Create a new id parented to the master,
		//since only satellites and the gateway will receive an id from the master node.
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
		for(NodeId node : allConnectedNodes.keySet())
		{
			A.say("Master " + nodeId + " sending package " + aPackage.toString() + " to " + node);
			
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
		//Can't dispatch to self.
	}
}
