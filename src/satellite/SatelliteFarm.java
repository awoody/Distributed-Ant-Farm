package satellite;

import communication.AbstractPackage;
import communication.InitializationPackage;

import engine.AbstractFarm;

public class SatelliteFarm extends AbstractFarm
{
	public AbstractPackage getPackageForNewAnt()
	{
		InitializationPackage newAntPackage = new InitializationPackage();
		
		newAntPackage.updateGrid(mainGrid);
		
		return newAntPackage;
	}
}
