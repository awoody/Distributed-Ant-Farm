package scratch;
import java.util.*;
import java.io.*;
import java.net.*;

public class Master extends Node{
	
	public Master(int id, int MyPort, String ipAddress) 
	{
		super(id, MyPort, ipAddress);
	}
	
	private Socket clientSocket;
	private Socket distributerSocket;
	public boolean isRunning = true;
	private DataInputStream distributerInput;
	//private LinkedList<Satellite> satellites;
	//private Satellite currentSatellite;
	
	public void initialize()
	{
		try 
		{
			this.listen();
			distributerSocket = listener.accept();//thread this shit
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		//temporary
		while (isRunning)
		{
			try 
			{
				distributerInput = new DataInputStream(distributerSocket.getInputStream());
				System.out.print("\nmaster: " + distributerInput);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	/*
	public void run()
	{
		while (isRunning)
		{
			try 
			{
				clientSocket = serverSocket.accept();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}*/
	
	

}
