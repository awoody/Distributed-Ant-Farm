package antImplementation;

import java.awt.Point;
import java.util.Random;

public enum Direction 
{
	UP, DOWN, LEFT, RIGHT;
	
	public static Point modifyPointByDirection(Point p, Direction d)
	{
		int x = p.x;
		int y = p.y;
		
		switch (d)
		{
		case UP:
			y -= 1;
			break;
		case DOWN:
			y += 1;
			break;
		case LEFT:
			x -= 1;
			break;
		case RIGHT:
			x += 1;
			break;
		}
		
		return new Point(x, y);
	}
	
	public static Direction randomDirection()
	{
		Random randy = new Random(System.currentTimeMillis());
		
		int value = randy.nextInt(4);
		
		if(value == 0)
			return UP;
		if(value == 1)
			return DOWN;
		if(value == 2)
			return LEFT;

		return RIGHT;
	}
}
