/**
 * 
 */
package gateway;

/**
 * Entry point for instantiating an instance of the
 * distributor.  This one's main method and class is 
 * not abstract, since it's unlikely that anyone
 * would want to override it; should they choose to, they
 * could extend it.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class DistributorInitializer
{
	/**
	 * Simply instantiates and runs a distributor.
	 * 
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Distributor().run();
	}
}
