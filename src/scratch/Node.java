package scratch;
import java.io.IOException;
import java.net.*;

public abstract class Node {
	
	public int nodeID;
	public int port;
	public String ip;
	public Socket connection;
	public ServerSocket listener;
	
	public Node(int id, int MyPort, String ipAddress)
	{
		nodeID = id;
		port = MyPort;
		ip = ipAddress;
		
	}
	
	public void listen()
	{
		try 
		{
			listener = new ServerSocket(this.port);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
	}
	
	public void connect(String ip, int port)
	{
		try 
		{
			connection= new Socket(ip, port);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public void close()
	{
		try {
			connection.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
