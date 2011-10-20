package antImplementation;

import communication.AbstractPackage;
import communication.NodeId;

public abstract class AntClientToSatellitePackage extends AbstractPackage
{
	private static final long serialVersionUID = 7858263482646696709L;
	
	private String dummyValue;
	private int antId;
	
	public AntClientToSatellitePackage(NodeId id)
	{
		super(id);
	}
	
	
}