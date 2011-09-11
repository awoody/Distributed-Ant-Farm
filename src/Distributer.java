import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class Distributer {
	public static void main(String[] args) {
		int port = 11000;
		
		Socket clientSocket = null;
		ServerSocket serverSocket = null;
		PrintWriter printWriter;
		
		try {
			serverSocket = new ServerSocket(9999);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		while (true)
		{
			try {
				clientSocket = serverSocket.accept();
				System.out.println("suggesting port: " + port);
				printWriter = new PrintWriter(clientSocket.getOutputStream(), true);
				printWriter.println(port);
				port++;
				
			} catch (IOException e) {
				e.printStackTrace();
			}				
			
		}
		

	}

}
