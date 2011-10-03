package utilities;

/**
 * A is short for 'assistant'.  This class is designed
 * to contain any methods which are generally helpful to anything
 * in our class hierarchy and structure, and is imported in most
 * of the major high-level objects.  
 * 
 * @author DeepBlue
 *
 */
public class A 
{
	public final static boolean isDebug = true;
	
	/**
	 * Centralized method that serves the same function as 
	 * System.out.println(), but allows you to turn output
	 * on and off for debugging purposes in addition to printing
	 * whatever is passed in to it.  If we use this instead of
	 * the system call, it provides an easy way to suppress debugging
	 * output for demonstrations, etc, without having to find all the 
	 * print statements we might have left behind.
	 * 
	 * @param o -> Any object at all to be printed.
	 */
	public static void say(Object o)
	{
		if(!isDebug)
			return;
		
		System.out.println(o);
	}
}
