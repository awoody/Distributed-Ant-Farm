package client;

/**
 * Entry point for instantiating the client and
 * client engine specific to this implementation.
 * This is abstract and specifices only that it
 * should have a main method.  This is to force
 * people using this architecture to create this
 * object and implement its main method.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public abstract class AbstractClientInitializer 
{
	/**
	 * This main method should instantiate a client,
	 * then instantiate an implementation of clientEngine,
	 * passing the client in as a parameter.  Finally, it
	 * should run the client on its own thread.
	 * 
	 * @param args
	 */
	public static void main(String [] args)
	{
		
	}
}
