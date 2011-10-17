package scratch;

import java.io.*;

public class Controller {

	/**
	 * @param args
	 */
	private static boolean isRunning = true;
	
	
	public static void main(String[] args) 
	{
		int input;
		Master master;
		Distributer distributer;
		int ports = 13000;
		int ids = 12000;
		
		master = new Master(ids, ports, "localhost");
		ports++;
		ids++;
		distributer = new Distributer(ids, ports, "localhost");
		ports++;
		ids++;
		
		
		while (isRunning)
		{
		
			System.out.println("\n\n\nMENU:\n1. Start Master\n2. Start Distributer\n3. Start Satellite\n4. Exit\n\nEnter Number:");
			
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			try 
			{
				String inputString = bufferRead.readLine();
				input = Integer.parseInt(inputString);
			} 
			catch (Exception e) {
				input = 0;
			}
			
			
			switch(input)
			{
				case 1:
					System.out.println("Started Master\n");
					master.initialize();
					break;
				case 2:
					System.out.println("Started Distributer");
					
					distributer.initialize(master.port);
					break;
				case 3:
					System.out.println("entered 3"); 
					break;
				case 4:
					System.out.println("Shutting Down.");
					try
					{
						master.close();
						distributer.close();
					}
					catch (Exception e){}
					isRunning = false;
					System.exit(0);
				default:
					System.out.println("fail");
					break;
						
			}
		}

	}

}
