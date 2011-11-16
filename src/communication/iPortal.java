package communication;

import monitor.Graph;
import rpc.AnnotatedObject;

public interface iPortal 
{
	public AnnotatedObject makeNewConnection(String annotatedObjectName);
	public Graph getNetworkGraph();
}
