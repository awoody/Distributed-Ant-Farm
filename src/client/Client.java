package client;
import java.net.Socket;
import java.io.*;


public class Client 
{
	private static Socket socket;
	private static PrintWriter printWriter;
	
	public static void main(String[] args)
	{		
		String msg = "a";		
		BufferedReader reader;

		try 
		{
			socket = new Socket("localhost",11000);
			BufferedReader portReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			int connectPort = Integer.parseInt(portReader.readLine());
			
			System.out.println("connecting to port: " + connectPort);
			socket = new Socket("localhost", connectPort);
			
			printWriter = new PrintWriter(socket.getOutputStream(), true);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		
		while (msg!=null)
		{
			reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				msg = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			printWriter.println(msg);
		}
		
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
