package communication;

import java.io.Serializable;

import utilities.A;

/**
 * Uniquely Identifies a specific message.
 * Across 1000 trials of 100000 independent
 * generations of this object, the mean time
 * to failure (two duplicate objects, which
 * is two of them with the same exact random
 * seed generated at the same time with the same
 * node id), is 24466.  This is more than acceptable
 * for the purposes of this object, since I don't
 * ever anticipate there being more than 13-14 of these
 * at a time.  The chance of a failure at that level
 * astronomically low.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class MessageId implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -359008628621226490L;
	private NodeId originNode;
	private long creationTime;
	private int randomSeed;
	
	public MessageId(NodeId originatingNode)
	{
		this.originNode = originatingNode;
		
		this.creationTime = System.currentTimeMillis();
		this.randomSeed = A.randomIntFromZeroToBound(999999999);
	}

		
	public String toString()
	{
		return "MessageId:" + this.hashCode();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (creationTime ^ (creationTime >>> 32));
		result = prime * result
				+ ((originNode == null) ? 0 : originNode.hashCode());
		result = prime * result + randomSeed;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessageId other = (MessageId) obj;
		if (creationTime != other.creationTime)
			return false;
		if (originNode == null) {
			if (other.originNode != null)
				return false;
		} else if (!originNode.equals(other.originNode))
			return false;
		if (randomSeed != other.randomSeed)
			return false;
		return true;
	}

}
