package utilities;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
	public static boolean surpressOutput;
	private static DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:SSSS");
	private static Random randomSeed;
	
	static
	{
		randomSeed = new Random(System.currentTimeMillis());
	}
	
	
	public void supressOutput(boolean suppress)
	{
		surpressOutput = suppress;
	}
	
	
	public static void printObjectArray(Object [][] blocks)
	{
		
			int iS = blocks.length;
			int jS = blocks[0].length;
			
			System.out.println("#################");
												  
			for(int i=0; i < iS; i++)
			{
				for(int j=0; j < jS; j++)
				{
					Object b = blocks[i][j];
					System.out.print(b.toString());
				}
				
				System.out.println();
			}
			
			System.out.println("#################");
		
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
	 * @param o - the object to print; usually a string.
	 */
	public static void say(Object o)
	{
		if(!isDebug || surpressOutput)
			return;
		
		Date date = new Date();	
		String currentTime = dateFormat.format(date);
		
		System.out.println("[" + currentTime + "]" + o);
	}
	
	
	/**
	 * Meant to be used for logging purposes; output from the networking
	 * framework which should never be suppressed.  This output, like error,
	 * will not be supressed when the debug flag is set to false.
	 * 
	 * @param o - the object to print; usually a string.
	 */
	public static void log(Object o)
	{
		if(surpressOutput)
			return;
		
		Date date = new Date();	
		String currentTime = dateFormat.format(date);
		
		System.out.println("LOG [" + currentTime + "]" + o);
	}
	
	
	/**
	 * Used only when an error has occured to notify the user of a serious
	 * problem with their setup or the state of the network.  Not supressed
	 * when the debug flag is turned off.
	 * 
	 * @param o - the object to print; usually a string.
	 */
	public static void error(Object o)
	{		
		Date date = new Date();	
		String currentTime = dateFormat.format(date);
		
		System.err.println("[" + currentTime + "]" + o);
	}
	
	
	/**
	 * Prints to standard error and then calls System.exit(1), assuming
	 * that a fatal error has occurred and execution must halt.
	 * 
	 * @param o - the object to print; usually a string.
	 */
	public static void fatalError(Object o)
	{		
		Date date = new Date();	
		String currentTime = dateFormat.format(date);
		
		System.err.println("[" + currentTime + "]" + o + " System will now exit.");
		System.exit(1);
	}
	
	
	public static String getSiteLocalAddress()
	{
		String hostName = "Unknown";
		
		try 
		{
			InetAddress host = InetAddress.getLocalHost();			
			hostName = host.getHostName();
			InetAddress [] allAddys = InetAddress.getAllByName(hostName);
			
			for(InetAddress addy : allAddys)
			{
				if(addy.isSiteLocalAddress())
				{
					A.log("Using site-local address: " + addy.getHostAddress());
					return addy.getHostAddress();
				}
			}
		} 
		catch (UnknownHostException e) 
		{
			A.error("Could not resolve a host to this machine.  No network components will work until this is resolved.");
			//e.printStackTrace();
		}
		
		A.error("Unable to discover a site-local address for host: " + hostName + ".  Returning a null address; no connection will be made.");
		return null;
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
