package communication;

import rpc.AnnotatedObject;

public interface iPortal 
{
	public void dispatchAsynchronousPackage(AbstractPackage aPackage, NodeId recipient);
	public Object dispatchSynchronousPackage(AbstractPackage aPackage, NodeId recipient);
	public AnnotatedObject makeNewConnection(String annotatedObjectName);
	public MessageId generateMessageId();
	public NodeId getNodeId();
}
