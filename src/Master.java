import java.net.ServerSocket;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.*;


public class Master {
	
	private static ServerSocket serverSocket;
	
	public static void main(String[] args)
	{
		int timeAlive = 0;
		LinkedList <Socket>clients = new LinkedList<Socket>();
		Socket clientSocket;
		BufferedReader bufferedReader = null;
		String inputLine;
		
		System.out.print("master started\n\n");
		
		//accept new connections
		try
		{
			for (int i=0;i<3;i++)
			{
				serverSocket = new ServerSocket(11000+i);
				clientSocket = serverSocket.accept();
				clients.add(clientSocket);
				System.out.print("connection accepted");
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
		//while(true)
		//{
			
			
			
			//loop thru every connection, and print out the new stuff
			Iterator<Socket> iterator = clients.iterator(); 
			while (iterator.hasNext())
			{
				try {
					bufferedReader = new BufferedReader(new InputStreamReader(iterator.next().getInputStream()));
					while ((inputLine = bufferedReader.readLine()) != null)
					{
						System.out.println(inputLine);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			try 
			{
				timeAlive++;
				System.out.println(timeAlive);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		//}
		
		
	}

}
