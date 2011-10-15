package antImplementation;

import communication.AbstractPackage;
import communication.NodeId;

public class AntDummyPackage extends AbstractPackage
{
	private static final long serialVersionUID = 7858263482646696709L;
	
	private String dummyValue;
	private NodeId sourceId;
	
	public AntDummyPackage(NodeId id)
	{
		super(id);
		sourceId = id;
	}
	
	public String getDummyValue()
	{
		return dummyValue;
	}
	
	public void setDummyValue(String s)
	{
		this.dummyValue = s;
	}
}
