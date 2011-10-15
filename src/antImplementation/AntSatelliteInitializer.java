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
		new SatelliteAntFarm(satellite);
		new Thread(satellite).start();
		
		SatelliteServer satellite2 = new SatelliteServer(11003);
		new SatelliteAntFarm(satellite2);
		new Thread(satellite2).start();
		
		SatelliteServer satellite3 = new SatelliteServer(11004);
		new SatelliteAntFarm(satellite3);
		new Thread(satellite3).start();
		
		SatelliteServer satellite4 = new SatelliteServer(11005);
		new SatelliteAntFarm(satellite4);
		new Thread(satellite4).start();
	}
}
