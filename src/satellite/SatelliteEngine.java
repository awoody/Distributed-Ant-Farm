package satellite;

import communication.AbstractPackage;
import communication.InitializationPackage;

import engine.AbstractEngine;

public class SatelliteEngine extends AbstractEngine
{
	public AbstractPackage getPackageForNewAnt()
	{
		InitializationPackage newAntPackage = new InitializationPackage();
		
		newAntPackage.updateGrid(mainGrid);
		
		return newAntPackage;
	}
}
