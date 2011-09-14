package utilities;

/**
 * A is short for 'assistant'.
 * @author DeepBlue
 *
 */
public class A 
{
	public final static boolean isDebug = true;
	
	public static void say(Object o)
	{
		if(!isDebug)
			return;
		
		System.out.println(o);
	}
}
