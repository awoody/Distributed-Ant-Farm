package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import rpc.AnnotatedObject;
import utilities.A;

import communication.NodeId;
import communication.Portal;
import communication.Recipient;
import communication.ResourceIdentificationPackage;

import constants.iConstants;

public class Server extends Portal implements Runnable
{
	private ServerSocket serverSocket;
	
	boolean isRunning = true;
	
	public Server(Recipient r, int portNumber, iConstants constants) 
	{
		super(r, constants);
		
		try 
		{
			serverSocket = new ServerSocket(portNumber);
			isRunning = true;
			
			this.connectToDistributor();
			
			String address = serverSocket.getInetAddress().getHostName();
			A.say("Address is: " +  address);
			distributor.registerNetworkResource(recipient.getResourceName(), portNumber, address, nodeId);
			
			Thread threadForServer = new Thread(this);
			threadForServer.start();
		} 
		catch (IOException e) 
		{
			A.error("Unable to start the server");
			isRunning = false;
			//e.printStackTrace();
		}
		// TODO Auto-generated constructor stub
	}


	@Override
	public void run() 
	{

		A.say("Server is running.");
		while (isRunning) 
		{
			Socket clientSocket;
			try 
			{
				clientSocket = serverSocket.accept();
				// When a new socket is created, assign it a node id,
				// add it to the map of all existing connections,
				// start a new thread for it and then send it
				// the recipient's implementation of an initialization
				// package.
				A.say("Processing a server connection.");
				NodeConnection newConnection = new NodeConnection(clientSocket);
				Thread newClientThread = new Thread(newConnection);
				newClientThread.start();
				
				//We need to get the nodeId and resource name from this new object so that we can properly set this up; this is done by creating
				//invocation packages which are destined for the recipient of the new connection, which can service the response we need.  The
				//nodeId of the recipient is set by the portal which contains it, NOT by the user, so they don't have any knowledge of nodeId at
				//all.
				//TODO: This could all be done in one call with a custom package, but this is the quick and easy way to do it.
				ResourceIdentificationPackage rip = new ResourceIdentificationPackage(nodeId, this.generateMessageId());
				
				Object [] response = (Object[]) newConnection.sendSynchronousPackage(rip);
				
				NodeId resourceId = (NodeId) response[0];
				String resourceName = (String) response[1];
				
				//Create the annotated object using the beautiful magic of Guice and pass it to the user's recipient.
				@SuppressWarnings("unchecked")
				AnnotatedObject userObject = injector.getInstance(constants.getAnnotatedObjectsByString().get(resourceName));
				userObject.setNodeId(resourceId);
				
				allConnections.put(resourceId, newConnection);
				recipient.newObjectHasConnected(userObject);
				
				//newConnection.sendAsynchronousPackage(recipient.packageForNewConnection(newNode));
				//allConnections.put(newNode, newConnection);
				//A.say("A new connection was accepted by the master, and assigned the nodeId " + newNode + " and connection " + newConnection);
			} 
			catch (IOException e) 
			{
				A.error("The server failed to make a connection.");
			}
		}

	}

}
