package antImplementation;

import java.util.HashSet;
import java.util.Set;

import utilities.A;
import communication.AbstractPackage;
import communication.NodeId;
import communication.Portal;
import engine.AbstractMasterEngine;

/**
 * This is the ant-farm implementation of the AbstractMasterEngine object.
 * This is where the general distributed server ends and the ant farm 
 * begins.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class MasterAntFarm extends AbstractMasterEngine
{

	public MasterAntFarm(Portal aPortal) 
	{
		super(aPortal);
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see engine.AbstractEngine#receivePackage(communication.AbstractPackage)
	 */
	public void receivePackage(AbstractPackage aPackage)
	{
		if(aPackage instanceof AntDummyPackage)
		{
			AntDummyPackage p = (AntDummyPackage) aPackage;
			A.say("Master Farm Recived a package: " + p.getDummyValue() + " from node: " + p.nodeId());
			p.setDummyValue("Message from master to satellites: Satellite " + p.nodeId() + " informed master of movement (" + p.getDummyValue() + ")");
			Set<NodeId> allNodes = new HashSet<NodeId>();
			allNodes.remove(p.nodeId()); //Don't send the message back to the original node.
			portal.dispatchPackage(aPackage, allNodes);
		}
	}
	
	public AbstractPackage packageForNewConnection(NodeId id)
	{
		AntInitializationPackage p = new AntInitializationPackage(portal.getNodeId());
		p.setIdForNewNode(id);
		
		return p;
	}
}
