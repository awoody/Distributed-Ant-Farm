package satellite;

/**
 * Another abstract initializer, meant to force people using
 * this architecture to implement this for their satellite
 * server object, allowing them to instantiate their specific
 * implementation object.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public abstract class AbstractSatelliteInitializer 
{
	/**
	 * This method should instantiate a satellite server,
	 * then an implementation of the satelite engine,
	 * passing the satellite server as an argument.  This
	 * will establish recipient-portal relationship.  Finally,
	 * the satellite server should be told to run.
	 * 
	 * @param args
	 */
	public static void main(String [] args)
	{
		
	}
}
