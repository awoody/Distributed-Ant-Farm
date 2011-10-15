package antImplementation;

import utilities.A;
import communication.AbstractPackage;
import communication.Portal;
import engine.AbstractClientEngine;

/**
 * This is the ant-farm implementation of the AbstractClientEngine object.
 * This is where the general distributed server ends and the ant farm 
 * begins.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class Ant extends AbstractClientEngine
{

	public Ant(Portal aPortal) 
	{
		super(aPortal);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void receivePackage(AbstractPackage aPackage)
	{
		A.say("Package arrived at the ant");
		
		if(aPackage instanceof AntDummyPackage)
			A.say("Dummy value received at ant: " + ((AntDummyPackage) aPackage).getDummyValue());
		
		return;
	}

}
