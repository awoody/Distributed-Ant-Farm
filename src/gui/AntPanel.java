package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.Random;

import javax.swing.JPanel;

public class AntPanel extends JPanel {

	private static final long serialVersionUID = 6275760759582642392L;

	protected AntPanel( ) {
		super();
		//addKeyListener( new ControlListener( gs, this ) );
		
		
	}
	
	
	
	@Override
	protected void paintComponent( Graphics g ) {
		
		super.paintComponent( g );
		
//		if( shift( ) ) {
//			activeRotation = getRotation( );
//		}
		
		Graphics2D g2d = (Graphics2D)g;
	}

}
