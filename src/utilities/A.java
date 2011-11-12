package utilities;

import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import rpc.AnnotatedObject;

import communication.NodeId;

/**
 * A is short for 'assistant'.  This class is designed
 * to contain any methods which are generally helpful to anything
 * in our class hierarchy and structure, and is imported in most
 * of the major high-level objects.  
 * 
 * @author DeepBlue
 * @param <E>
 *
 */
public class A 
{
	public final static boolean isDebug = false;
	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSSS");
	private static Random randomSeed;
	
	static
	{
		randomSeed = new Random(System.currentTimeMillis());
	}
	
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
		
		Date date = new Date();
		
		String currentTime = dateFormat.format(date);
		
		System.out.println("[" + currentTime + "]" + o);
	}
	
	
	public static void error(Object o)
	{
		if(!isDebug)
			return;
		
		Date date = new Date();
		
		String currentTime = dateFormat.format(date);
		
		System.err.println("[" + currentTime + "]" + o);
	}
	
	
	public static Map<String, Method> mapMethods(Object objToMap)
	{
		Map<String, Method> map = new HashMap<String, Method>();
		
		for(Method m : objToMap.getClass().getMethods())
		{
			map.put(m.getName(), m);
		}
		
		return map;
	}

	
	public static int randomIntFromZeroToBound(int upperBound)
	{
		return randomSeed.nextInt(upperBound);
	}
	
	
	public static double average(int [] values)
	{
		double total = 0;
		
		for(int val : values)
		{
			total+=val;
		}
		
		return total/values.length;
	}
	
	
	public static double standardDeviation(int [] values, double average)
	{
		double variance = 0;
		
		for(int val : values)
		{
			variance += Math.sqrt(Math.pow(val - average, 2));
		}
		
		return Math.pow(variance, 2);
	}
	
	
	public static List<NodeId> searchForNodeIds(Object [] objArray)
	{
		List<NodeId> nodeIdList = new ArrayList<NodeId>();
		
		for(Object o : objArray)
		{
			if(o instanceof AnnotatedObject)
			{
				AnnotatedObject ao = (AnnotatedObject) o;
				nodeIdList.add(ao.getNodeId());
			}
			
			if(o instanceof List<?>)
			{
				List<?> list = (List<?>) o;
				
				for(Object o2 : list)
				{
					if(o2 instanceof AnnotatedObject)
					{
						AnnotatedObject ao = (AnnotatedObject) o2;
						nodeIdList.add(ao.getNodeId());
					}
				}
			}
		}
		
		return nodeIdList;
	}
	
}
