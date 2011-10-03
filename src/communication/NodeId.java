package communication;

/**
 * A wrapper class for an integer which represents a node
 * connected to the network.  That node can be either a client,
 * a distributor, a satellite server or the master server.  Each
 * should have its own unique nodeId associated with it.  I decided
 * to make this a class because we'll almost certainly want more functionality
 * in this object as time goes on.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class NodeId 
{
	private int nodeId;
	
	public void setId(int Id)
	{
		nodeId = Id;
	}
	
	public int getId()
	{
		return nodeId;
	}
}
