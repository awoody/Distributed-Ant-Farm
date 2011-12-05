package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.border.EtchedBorder;

import monitor.Node;
import monitor.iNodeStatus;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;

public class NodeParrotListener implements GraphMouseListener<Node> {

	private static final PopupFactory factory;
	private final JPanel messagePanel;
	private Popup visiblePopup;
	private Node activeNode;
	
	static {
		factory = new PopupFactory( );
	}
	
	public NodeParrotListener( ) {
		
		messagePanel = new JPanel( );
		messagePanel.setPreferredSize( new Dimension( 180, 100 ) );
		messagePanel.setBorder( new EtchedBorder(Color.blue, Color.cyan ) );
		messagePanel.setBackground( Color.white );
		
		messagePanel.setLayout( 
				new BoxLayout( messagePanel, BoxLayout.Y_AXIS ) );
		
		visiblePopup = null;
		activeNode = null;
	}
	
	private void addStats( Node n ) {
		iNodeStatus stats = n.getNodeStatus();
		//title label
		Label title = new Label( "Node id: " + n.getNodeId( ) );
		messagePanel.add( title );
		if( stats == null )
			return;
		
		//extract stats
		double meanLatency = stats.getAverageLatency();
		double packagesPerSec = stats.getPackagesPerSecond( );
		int nodeCount = stats.totalConnectedNodes();
		
		//create labels
		Label latency 	= new Label( "Average Latency: " + meanLatency );
		Label packages 	= new Label( "Packages Per Second: " + packagesPerSec );
		Label nodes 	= new Label( "Connected Nodes: " + nodeCount );
		
		//set all backgrounds to white
		title.setBackground( Color.white );
		latency.setBackground( Color.white );
		packages.setBackground( Color.white );
		nodes.setBackground( Color.white );
		
		messagePanel.add( latency );
		messagePanel.add( packages );
		messagePanel.add( nodes );
	}
	
	
	@Override
	public void graphClicked( Node n, MouseEvent e ) {
		if( visiblePopup != null ) {
			visiblePopup.hide( );
			if( activeNode.equals( n ) ) {
				visiblePopup = null;
				activeNode = null;
				return;
			}
		}
		
		messagePanel.removeAll( );
		
		addStats( n );
		
		visiblePopup = factory.getPopup( 
				null, messagePanel, e.getXOnScreen( ), e.getYOnScreen( ));
		visiblePopup.show();
		activeNode = n;
		
	}

	@Override
	public void graphPressed( Node n, MouseEvent e ) {
		
	}

	@Override
	public void graphReleased( Node n, MouseEvent e ) {
		
	}

}
