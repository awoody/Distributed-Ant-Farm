package antImplementation;

import satellite.AbstractSatelliteInitializer;
import satellite.SatelliteServer;

/**
 * Ant specific initializer for satellite, ensures that an satelliteAntFarm is properly instantiated
 * with the client, and that the client is run.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class AntSatelliteInitializer extends AbstractSatelliteInitializer
{
	public static void main(String[] args) 
	{
		SatelliteServer satellite = new SatelliteServer(11002);
		new Thread(new SatelliteAntFarm(satellite)).start();
		new Thread(satellite).start();
	}	
}
