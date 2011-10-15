package antImplementation;

import master.AbstractMasterInitializer;
import master.Master;

/**
 * Ant specific initializer for master, ensures that an masterAntFarm is properly instantiated
 * with the client, and that the client is run.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class AntMasterInitializer extends AbstractMasterInitializer
{
	public static void main(String[] args) 
	{
		Master master = new Master();
		new MasterAntFarm(master);
		new Thread(master).run();
	}
}
