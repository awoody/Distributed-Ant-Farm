package antImplementation;

import java.awt.Point;

public enum Direction 
{
	UP, DOWN, LEFT, RIGHT;
	
	public Point modifyPointByDirection(Point p, Direction d)
	{
		int x = p.x;
		int y = p.y;
		
		switch(d)
		{
			case UP: y -= 1;
			case DOWN: y += 1;
			case LEFT: x -= 1;
			case RIGHT: x += 1;
		}
		
		return new Point(x, y);
	}
}
