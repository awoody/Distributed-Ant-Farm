package master;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;

import utilities.A;

//master server
public class Master
{
	
	private ServerSocket serverSocket;
	
	private final boolean run;
	
	private final LinkedList <Socket>clients;
		
	public Master()
	{
		clients = new LinkedList<Socket>();
		run = true;
		try 
		{
			serverSocket = new ServerSocket(11000);
		}
		catch (IOException e) 
		{
			e.printStackTrace();
			A.say("Unable to create the server socket.");
		}
		
		Thread connectionThread = new Thread(new ConnectionListener());
		connectionThread.run(); //Have a nice day!
	}
	
	public static void main(String[] args)
	{
		int timeAlive = 0;
		Socket clientSocket;
		BufferedReader bufferedReader = null;
		String inputLine;
		
		System.out.print("master started\n\n");
		
		Master p = new Master();
		
		// loop thru every connection, and print out the new stuff
		Iterator<Socket> iterator = p.clients.iterator();
		while (iterator.hasNext())
		{
			try 
			{
				bufferedReader = new BufferedReader(new InputStreamReader(iterator.next().getInputStream()));
				
				while ((inputLine = bufferedReader.readLine()) != null) 
				{
					System.out.println(inputLine);
				}
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}

		try 
		{
			timeAlive++;
			System.out.println(timeAlive);
			Thread.sleep(1000);
		} 
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}		
	}
	
	private class ConnectionListener implements Runnable
	{

		@Override
		public void run()
		{
			while(run)
			{
				Socket clientSocket;
				try
				{
					clientSocket = serverSocket.accept();
					clients.add(clientSocket);
					A.say("connection accepted");
				} 
				catch (IOException e) 
				{
					A.say("Failed to make a connection to a client");
				}
				
			}
			
		}
		
	}

}
