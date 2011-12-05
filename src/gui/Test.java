package gui;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Shape;

import javax.swing.JFrame;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Tree;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.decorators.EllipseVertexShapeTransformer;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.BasicVertexLabelRenderer;
import edu.uci.ics.jung.visualization.renderers.Renderer;

public class Test extends JFrame
{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4139080617004354672L;

	private class Viewer extends VisualizationViewer<Integer, String> {

		public Viewer(Layout<Integer, String> layout)
		{
			super(layout);
		}
		
		public Viewer( Tree<Integer, String> tree ) {
			super( new TreeLayout<Integer, String>( tree, 100 ), 
							new Dimension( 300, 300 ) );
			addRenderer( );
		}
		
		private void addRenderer( ) {
			/*setRenderer( new BasicRenderer<Integer, String>( ) {
				public void renderVertex(RenderContext<Integer,String> rc, 
						Layout<Integer, String> layout, Node n) {
				}
			} );*/
			
			/*renderContext.setVertexLabelRenderer( 
					new DefaultVertexLabelRenderer( Color.blue ) {
						private static final long serialVersionUID = -7520451226818123324L;
						
						public <T> Component getVertexLabelRendererComponent(JComponent vv, Object value,
								Font font, boolean isSelected, T vertex) {
									Choice c = new Choice( );
									c.add( "red" );
									return c;
							
						}
						
					} );*/
			/*renderContext.setVertexLabelTransformer( 
					new Transformer<Integer, String>( ){
				
				@Override
				public String transform(Integer arg0)
				{
					return "test";
				}
			});*/
			//new BasicVertexLabelRenderer.InsidePositioner();
			renderContext.setVertexLabelTransformer( new ToStringLabeller<Integer>( ) );
			renderer.getVertexLabelRenderer().setPositioner( new BasicVertexLabelRenderer.InsidePositioner( ) );
			renderer.getVertexLabelRenderer( ).setPosition( Renderer.VertexLabel.Position.CNTR );
			renderContext.setVertexShapeTransformer(new EllipseVertexShapeTransformer<Integer>() {
				//TODO
				public Shape transform( Integer i ) {
					return new Rectangle( 0, 0, 60, 30 );
				}
			});
		}
		
	}

	private Test( ) {
		super( "test" );
		DirectedSparseGraph<Integer, String> graph = 
				new DirectedSparseGraph<Integer, String>( );
		DelegateTree<Integer, String> tree = 
				new DelegateTree<Integer, String>( graph );
		
		tree.addVertex( 1 );
		tree.addChild( "1->2", 1, 2 );
		
		//VisualizationViewer<Integer, String> view = new VisualizationViewer<Integer, String>( new TreeLayout<Integer, String>( tree ) );
		VisualizationViewer<Integer, String> view = new Viewer( tree );
		//view.getRenderContext().setVertexLabelTransformer( new ToStringLabeller<Integer>() );
		//view.getRenderer( ).getVertexLabelRenderer( ).setPositioner( new BasicVertexLabelRenderer.InsidePositioner( ) );
		//view.getRenderer( ).getVertexLabelRenderer( ).setPosition( Renderer.VertexLabel.Position.CNTR );
		//BasicRenderer<Integer, String> renderer = new BasicRenderer<Integer, String>( );
		//renderer.setVertexRenderer(new GradientVertexRenderer<Integer, String>(Color.red, Color.blue, true ) );
		
		//final GraphZoomScrollPane panel = new GraphZoomScrollPane( view );
		//add( panel );
		add( view );
		tree.removeVertex( 1 );
		
		setDefaultCloseOperation( EXIT_ON_CLOSE );
		pack();
		setVisible( true );
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		new Test( );
	}

}
