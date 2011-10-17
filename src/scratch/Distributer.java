package scratch;
import java.io.*;
import java.net.*;

public class Distributer extends Node{
	
	public Distributer(int id, int MyPort, String ipAddress)
	{
		super(id, MyPort, ipAddress);
	}

	private String masterIP = "127.0.0.1";
	private PrintStream output;
	
	public void initialize(int masterPort)
	{
		try 
		{
			this.connect(masterIP, masterPort);
			output = new PrintStream(this.connection.getOutputStream());
			output.println("distro: 1\n");
			output.println("distro: 2\n");
			output.println("distro: 3\n");
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

}
