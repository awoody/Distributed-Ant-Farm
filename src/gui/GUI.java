package gui;

import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;

import monitor.Graph;
import monitor.Node;

import communication.NodeId;
import communication.PortalStatus;

import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.MouseListenerTranslator;

public class GUI extends JFrame implements Runnable{

	private static final int pause = 1000;
	private static final long serialVersionUID = 774383016016239497L;
	private ObservableDAFTree<String> tree;
	private final VisualizationViewer<Node, String> viewer;
	private final StatPanel statPanel;
	
	public GUI( Graph g ) {
		super( "DAF Network Graph" );

		
		setDefaultCloseOperation( EXIT_ON_CLOSE );

		statPanel = new StatPanel( g );
		
		tree = new ObservableDAFTree<String>( );
		setTree( g );

		viewer = new DAFViewer( tree );
		viewer.addMouseListener( 
				new MouseListenerTranslator<Node, String>( 
						new NodeParrotListener( ), viewer ) );
		viewer.setBackground( Color.white );
		
		GraphZoomScrollPane panel = new GraphZoomScrollPane( viewer );
		panel.addMouseWheelListener( getWheelListener( panel ) );
		
		
		//add tabs
		JTabbedPane tabs = new JTabbedPane( JTabbedPane.TOP );
		tabs.add( "Network Graph", panel );
		tabs.add( "Average Global Stats", statPanel );
		
		//add( panel );
		add( tabs );
		pack( );
		setLocationRelativeTo( null );
	}
	
	public void setTree( Graph g ) {
		tree.clear( );
		if( g == null ) {
			//test data
			Node n1 = new Node( new NodeId( 1, 0 ) ), 
					n2 = new Node( new NodeId( 2, 1 ) ),
					n3 = new Node( new NodeId( 3, 1 ) ),
					n21 = new Node( new NodeId( 4, 2 ) ),
					n22 = new Node( new NodeId( 5, 2 ) ),
					n31 = new Node( new NodeId( 6, 3 ) ),
					n11 = new Node( new NodeId( 7, 1 ) );
			
			n1.setNodeStatus( 
					new PortalStatus( 5.8, 18.0, 
							1, 3 ) );
			n2.setNodeStatus( 
					new PortalStatus( 5.8, 18.0, 
							2, 3 ) );
			n3.setNodeStatus( 
					new PortalStatus( 5.8, 18.0, 
							2, 3 ) );
			n21.setNodeStatus( 
					new PortalStatus( 5.8, 18.0, 
							0, 3 ) );
			n22.setNodeStatus( 
					new PortalStatus( 5.8, 18.0, 
							0, 3 ) );
			n31.setNodeStatus( 
					new PortalStatus( 5.8, 18.0, 
							0, 3 ) );
			n11.setNodeStatus( 
					new PortalStatus( 5.8, 18.0, 
							1, 3 ) );
			
			tree.addVertex( n1 );
			tree.addChild( "1->7", n1, n11, EdgeType.DIRECTED );
			tree.addChild( "1->2", n1, n2, EdgeType.DIRECTED );
			tree.addChild( "1->3", n1, n3, EdgeType.DIRECTED );
			tree.addChild( "2->4", n2, n21 );
			tree.addChild( "2->5", n2, n22 );
			tree.addChild( "3->6", n3, n31 );
			return;
		}
		
		if( g.getRootNode( ) == null ) return;
		
		Node activeNode;
		/*ArrayList<Node> toAdd = new ArrayList<Node> ( 
				g.getRootNode()
				.getEdges( ) );*/
		ArrayList<Node> toAdd = new ArrayList<Node>( );
		toAdd.add( g.getRootNode( ) );
		tree.addVertex( g.getRootNode( ) );
		
		//for each node...
		while( !toAdd.isEmpty( ) ) {
				activeNode = toAdd.remove( 0 );
				//for each out edge from this node...
				for( Node edge: activeNode.getEdges( ) ) {
					
					//prevent an edge(and node) from being added more than once
					if( tree.containsEdge( edge.toString( ) ) ) continue;
					
					//take care of child's children later
					toAdd.add( edge );
					
					//add child node and edge
					tree.addChild( 
							edge.toString( ), activeNode, 
							edge, EdgeType.DIRECTED );
				}
		}
		
		statPanel.setGraph( g );
		
		//viewer.repaint( );
	}
	
	private MouseWheelListener getWheelListener( final GraphZoomScrollPane p ) {
		return new MouseWheelListener( ) {
			
			@Override
			public void mouseWheelMoved( MouseWheelEvent e ) {
				JScrollBar scroll = e.getModifiers( ) == 0 ? 
							p.getHorizontalScrollBar( ) :
							p.getVerticalScrollBar( );
				scroll.setValue( scroll.getValue( ) + e.getUnitsToScroll( ) );
			}
		};
	}
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		Graph g = new Graph( );
		NodeId id1 = new NodeId( 1, 0 );
		NodeId id2 = new NodeId( 2, 1 );
		NodeId id3 = new NodeId( 3, 1 );
		
		Map<NodeId, Node> map = g.getNodeMap( );
		
		g.addNode( id1 );
		g.addNode( id2 );
		g.addNode( id3 );
		
		g.addEdge( id1, id2 );
		//g.addEdge( id2, id1 );
		g.addEdge( id1, id3 );
		//g.addEdge( id3, id1 );
		
		g.setRootNode( map.get( id1 ) );
		
		map.get( id1 ).addConnection( map.get( id2 ) );
		map.get( id1 ).addConnection( map.get( id3 ) );
		
		map.get( id1 ).setNodeStatus( 
				new PortalStatus( 5.8, 18.0, 
						1, 3 ) );
		map.get( id2 ).setNodeStatus( 
				new PortalStatus( 5.8, 18.0, 
						1, 3 ) );
		map.get( id3 ).setNodeStatus( 
				new PortalStatus( 5.8, 18.0, 
						1, 3 ) );
		
		GUI ui = new GUI( g );
		ui.setVisible( true );
		//gui.mainLoop( );
		
	}

	
	@Override
	public void run()
	{
		while( true ) {
			try {
				
				Thread.sleep( pause );
			} catch( InterruptedException e ) {
				System.exit( 1 );
			}
		}
	}

}
