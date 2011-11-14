package communication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import rpc.AnnotatedObject;

import utilities.A;

/**
 * This class is abstract and meant to be extended by anyone wishing
 * to use this framework.  An implementation of this object is passed
 * to the constructor of all portals, and is used by that portal to handle
 * any non-framework traffic which comes in over the network.  This object
 * should be coupled with an annotated object, and both should implement the
 * same interface.  When, on your local machine, you call a method in an
 * annotated object implementing the same methods as a recipient, the recipient
 * is the object that actually handles those method calls and provides them with
 * return values and updated information.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public abstract class Recipient
{
	protected Map<String, Method> methodMap;
	private Object methodServer;
	private NodeId nodeId;
	
	/**
	 * No arguments are needed.
	 */
	public Recipient()
	{
		methodMap = A.mapMethods(this);
		methodServer = this;
	}
	
	
	//This intentionally has no modifier; making it visible to class
	//and package, but not subclass and world; users cannot see this
	//method when implementing.
	void setNodeId(NodeId id)
	{
		this.nodeId = id;
	}
	
	public abstract void newObjectHasConnected(AnnotatedObject newObject);
	public abstract String getResourceName();
	
	//This intentionally has no modifier; making it visible to class
	//and package, but not subclass and world; users cannot see this
	//method when implementing.
	NodeId getNodeId()
	{
		return nodeId;
	}
	
	
	/**
	 * Takes a method name and an array of objects, and invokes them on 
	 * this recipient, assuming that you have extended recipient with your
	 * own class, which provides the method to be invoked.
	 * 
	 * @param methodName
	 * @param arguments
	 * @return
	 */
	public Object invokeMethod(String methodName, Object[] arguments)
	{
		Method methodForDelegate = methodMap.get(methodName);
		
		Object o = null;
		try 
		{
			o = methodForDelegate.invoke(methodServer, arguments);
		} 
		catch (IllegalArgumentException e) 
		{
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) 
		{
			e.printStackTrace();
		} catch (InvocationTargetException e) 
		{
			e.printStackTrace();
		}
		
		return o;
	}	
}
