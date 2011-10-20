package antImplementation;

import client.AbstractClientInitializer;
import client.Client;

/**
 * Ant specific initializer for client, ensures that an ant is properly instantiated
 * with the client, and that the client is run.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class AntClientInitializer extends AbstractClientInitializer 
{
	public static void main(String[] args) 
	{
		Client client = new Client();
		Ant ant = new Ant(client);
		Thread a = new Thread(ant);
		Thread b = new Thread(client);
		a.start();
		b.start();
	}
}
