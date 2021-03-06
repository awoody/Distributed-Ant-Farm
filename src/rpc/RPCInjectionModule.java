package rpc;





import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import packages.InvocationPackage;
import packages.MessageId;

import com.google.inject.AbstractModule;
import com.google.inject.matcher.Matchers;
import communication.Portal;

public class RPCInjectionModule extends AbstractModule
{
	private Portal portal;
	
	public RPCInjectionModule(Portal portal)
	{
		this.portal = portal;
	}
	
	@Override
	protected void configure() 
	{		
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(Synchronous.class), 
		        new SynchronousInterceptor());	
		
		bindInterceptor(Matchers.any(), Matchers.annotatedWith(Asynchronous.class), 
		        new AsynchronousInterceptor());	
	}
	
		
	public class SynchronousInterceptor implements MethodInterceptor
	{	
		@Override
		public Object invoke(MethodInvocation m) throws Throwable 
		{
			//A.say("Intercepted a synchronous method call");
			AnnotatedObject callingObject = (AnnotatedObject) m.getThis();
			//Create the invocation package for the message.
			MessageId outgoingMessageId = portal.generateMessageId();
			InvocationPackage ip = new InvocationPackage(portal.getNodeId(), outgoingMessageId, m.getMethod().getName(), MessageType.SYNCHRONOUS, m.getArguments());
			
			return portal.dispatchSynchronousPackage(ip, callingObject.getNodeId());
		}
	}
	
	public class AsynchronousInterceptor implements MethodInterceptor
	{	
		@Override
		public Object invoke(MethodInvocation m) throws Throwable 
		{
			AnnotatedObject callingObject = (AnnotatedObject) m.getThis();
			//Create the invocation package for the message.
			MessageId outgoingMessageId = portal.generateMessageId();
			InvocationPackage ip = new InvocationPackage(portal.getNodeId(), outgoingMessageId, m.getMethod().getName(), MessageType.ASYNCHRONOUS, m.getArguments());
						
			portal.dispatchAsynchronousPackage(ip, callingObject.getNodeId());
			return m.proceed();
		}
	}
	
	
}
