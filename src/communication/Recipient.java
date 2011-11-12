package communication;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import rpc.AnnotatedObject;

import utilities.A;

public abstract class Recipient
{
	protected Map<String, Method> methodMap;
	private Object methodServer;
	private NodeId nodeId;
	
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
	
	/**
	 * Returns the NodeId of this recipient (same as it's corresponding portal).
	 * The nodeId is a URI for the networking architecture, guaranteed to be unique
	 * across all objects connected to the network.  May be useful to use as your
	 * URI, but you're welcome to implement your own as well.
	 * 
	 * @return - the nodeId of the portal connected to this recipient.
	 */
	protected NodeId getNodeId()
	{
		return nodeId;
	}
	
	
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
