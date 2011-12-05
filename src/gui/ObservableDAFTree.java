package gui;

import java.util.ArrayList;
import java.util.List;

import monitor.Node;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.event.GraphEvent;
import edu.uci.ics.jung.graph.event.GraphEventListener;
import edu.uci.ics.jung.graph.event.GraphEvent.Type;
import edu.uci.ics.jung.graph.util.EdgeType;

public class ObservableDAFTree<E> extends DelegateTree<Node, E> {

	private static final long serialVersionUID = 6916074551257964161L;
	
	private final List<GraphEventListener<Node, E>> listeners;
	
	public ObservableDAFTree( ) {
		super( new DirectedSparseGraph<Node, E>( ));
		listeners = new ArrayList<GraphEventListener<Node, E>>( );
	}
	
	private void notifyListeners( GraphEvent<Node, E> e ) {
		for( GraphEventListener<Node, E> l: listeners ) {
			l.handleGraphEvent( e );
		}
	}

	@Override
	public boolean addChild( E edge, Node parent, 
			Node child, EdgeType edgeType ) {
		
		//add child
		notifyListeners( 
				new GraphEvent.Vertex<Node, E>
				(this, Type.VERTEX_ADDED, child ) );
		//add edge to child
		notifyListeners( 
				new GraphEvent.Edge<Node, E>
				(this, Type.EDGE_ADDED, edge ) );
		
		return super.addChild(edge, parent, child, edgeType);
	}

	@Override
	public boolean addChild(E edge, Node parent, Node child)
	{
		return addChild( edge, parent, child, EdgeType.DIRECTED );
	}

	@Override
	public boolean removeChild( Node orphan )
	{
		
		//remove link from parent
		notifyListeners( 
				new GraphEvent.Edge<Node, E>
				(this, Type.EDGE_REMOVED, this.getParentEdge( orphan ) ) );
		
		//remove node
		notifyListeners( 
				new GraphEvent.Vertex<Node, E>
				( this, Type.VERTEX_REMOVED, orphan ) );
		
		
		return super.removeChild(orphan);
	}

	@Override
	public boolean removeVertex( Node n )
	{
		return removeChild( n );
	}
	
	public void addGraphEventListener( GraphEventListener<Node, E> l ) {
		listeners.add( l );
	}
	
	public void clear( ) {
		removeVertex( getRoot( ) );
	}
	
}
