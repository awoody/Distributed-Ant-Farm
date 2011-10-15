package antImplementation;

import java.util.HashSet;
import java.util.Set;

import utilities.A;
import communication.AbstractPackage;
import communication.NodeId;
import communication.Portal;
import engine.AbstractSatelliteEngine;

/**
 * This is the ant-farm implementation of the AbstractSatelliteEngine object.
 * This is where the general distributed server ends and the ant farm 
 * begins.
 * 
 * @author Alex Woody
 *         CS 587 Fall 2011 - DAF Project Group
 *
 */
public class SatelliteAntFarm extends AbstractSatelliteEngine
{

	public SatelliteAntFarm(Portal aPortal) 
	{
		super(aPortal);
		// TODO Auto-generated constructor stub
	}
	
	public void receivePackage(AbstractPackage aPackage)
	{
		if(aPackage instanceof AntDummyPackage)
		{
			AntDummyPackage p = (AntDummyPackage) aPackage;
			A.say("Satellite Farm Recived a package: " + p.getDummyValue() + " from node: " + p.nodeId());

			AntDummyPackage notificationForMaster = new AntDummyPackage(portal.getNodeId());
			notificationForMaster.setDummyValue("Client " + p.nodeId() + " performed an action.");
			portal.dispatchDirectlyToMaster(notificationForMaster);
			
			p.setDummyValue("Response from satellite back to ant.");
			Set<NodeId> allNodes = new HashSet<NodeId>();
			allNodes.add(p.nodeId());
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
