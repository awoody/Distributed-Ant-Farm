package communication;

public class DistributorPackage extends AbstractPackage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4228963288759376825L;
	int newPortId;
	
	public DistributorPackage(NodeId id, int newPortId)
	{
		super(id);
		this.newPortId = newPortId;
		// TODO Auto-generated constructor stub
	}

	public int getNewPortId() 
	{
		return newPortId;
	}

	public void setNewPortId(int newPortId) 
	{
		this.newPortId = newPortId;
	}
}
