package gui;

import java.awt.Color;
import java.awt.Label;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import monitor.Graph;
import monitor.Node;
import monitor.iNodeStatus;

public class StatPanel extends JPanel {

	private static final long serialVersionUID = -1655143241753992082L;
	
	private Label latency;
	private Label packages;
	private Label connections;
	
	protected StatPanel( Graph g ) {
		super( );
		setLayout( new BoxLayout( this, BoxLayout.Y_AXIS ) );
		setBackground( Color.white );
		
		latency = new Label( "" );
		packages = new Label( "" );
		connections = new Label( "" );
		
		latency.setBackground( Color.white );
		packages.setBackground( Color.white );
		connections.setBackground( Color.white );
		
		setGraph( g );
		
		add( latency );
		add( packages );
		add( connections );
	}
	
	public void setGraph( Graph g ) {
		if( g == null ) return;
		if( g.getRootNode( ) == null ) return;
		
		Node activeNode;
		ArrayList<Node> toAdd = new ArrayList<Node>( );
		toAdd.add( g.getRootNode( ) );
		iNodeStatus stats = g.getRootNode( ).getNodeStatus();
		double latencySum = stats.getAverageLatency( );
		double packagesSum = stats.getPackagesPerSecond( );
		int connectionsSum = stats.totalConnectedNodes( );
		int nodeCount = 1;
		
		//for each node...
		while( !toAdd.isEmpty( ) ) {
				activeNode = toAdd.remove( 0 );
				//for each out edge from this node...
				for( Node edge: activeNode.getEdges( ) ) {
					
					//take care of child's children later
					toAdd.add( edge );
					
					nodeCount++;
					stats = edge.getNodeStatus( );
					latencySum += stats.getAverageLatency( );
					packagesSum += stats.getPackagesPerSecond( );
					connectionsSum += stats.totalConnectedNodes( );
					
				}
		}
		
		latency.setText( "Average latency per Node: " + 
					latencySum / nodeCount );
		packages.setText( "Average Packages per second per Node: " + 
					packagesSum / nodeCount );
		connections.setText( "Average connections per Node: " + 
					connectionsSum / nodeCount );
	}

}
