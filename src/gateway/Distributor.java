package gateway;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import communication.AbstractPortal;


public class Distributor extends AbstractPortal
{
	public void run() 
	{
		int port = 11000;
		
		Socket clientSocket = null;
		ServerSocket serverSocket = null;
		PrintWriter printWriter;
		
		try 
		{
			serverSocket = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true)
		{
			try 
			{
				clientSocket = serverSocket.accept();
				System.out.println("suggesting port: " + port);
				printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				printWriter.println(port);
				port++;
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}				
			
		}
		

	}

}
