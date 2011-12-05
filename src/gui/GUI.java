package gui;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import monitor.Graph;
import monitor.Node;

import org.apache.commons.collections15.Transformer;

import communication.NodeId;

import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.MouseListenerTranslator;

public class GUI extends JFrame implements Runnable{

	private static final int pause = 1000;
	private static final long serialVersionUID = 774383016016239497L;
	private ObservableDAFTree<String> tree;
	private final VisualizationViewer<Node, String> viewer;
	
	private GUI( Graph g ) {
		super( "DAF Network Graph" );
		setDefaultCloseOperation( EXIT_ON_CLOSE );

		setTree( g );
		
		viewer = new DAFViewer( tree );
			//new VisualizationViewer<Node, String>
				//( new TreeLayout<Node, String>( tree ) );//, 
						//new Dimension( 600, 400 ) );
		viewer.addMouseListener( 
				new MouseListenerTranslator<Node, String>( 
						new NodeParrotListener( ), viewer ) );
		viewer.setBackground( Color.white );
		//TODO figure out how to add label to inside using BasicVertexLabelrenderer
		//break. ha!
		
		
		
		add( viewer );
		pack( );
		setLocationRelativeTo( null );
	}
	
	public void setTree( Graph g ) {
		tree.clear( );// = new ObservableDAFTree<String>( );
		if( g == null ) {
			//test data
			Node n1 = new Node( new NodeId( 1, 0 ) ), 
					n2 = new Node( new NodeId( 2, 1 ) ),
					n3 = new Node( new NodeId( 3, 1 ) ),
					n4 = new Node( new NodeId( 4, 2 ) );
			tree.addVertex( n1 );
			tree.addChild( "1->2", n1, n2, EdgeType.DIRECTED );
			tree.addChild( "1->3", n1, n3, EdgeType.DIRECTED );
			tree.addChild( "2->4", n2, n4 );
			return;
		}
		
		Node activeNode;
		ArrayList<Node> toAdd = new ArrayList<Node> ( 
				g.getRootNode().getEdges( ) );
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
	}
	
	/**
	 * @param args
	 */
	public static void main( String[] args ) {
		GUI ui = new GUI( null );
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
