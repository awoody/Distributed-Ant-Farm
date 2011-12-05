package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;

import monitor.Node;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class DAFViewer extends VisualizationViewer<Node, String> {

	private static final long serialVersionUID = 7881989060061703677L;

	public DAFViewer(Layout<Node, String> layout, Dimension d ) {
		super(layout, d);
	}
	
	public DAFViewer( Tree<Node, String> t, Dimension d ) {
		this( new TreeLayout<Node, String>( t, 150 ), d );
		addRenderer( );
	}
	
	public DAFViewer( Tree<Node, String> t ) {
		this( t, new Dimension( 600, 600 ) );
	}
	
	private void addRenderer( ) {
		renderer.getVertexLabelRenderer( ).setPositioner( 
				new BasicVertexLabelRenderer.InsidePositioner( ) );
		renderer.getVertexLabelRenderer( ).setPosition( 
				Renderer.VertexLabel.Position.CNTR );
		renderContext.setVertexLabelTransformer( 
				new ToStringLabeller<Node>( ) );
		renderContext.setVertexShapeTransformer(
				new EllipseVertexShapeTransformer<Node>() {
					public Shape transform( Node n ) {
						return new Rectangle( 0, 0, 110, 30 );
					}
				});
	}

}
