package antImplementation;

import java.awt.Point;

import communication.NodeId;

public class AntMovementPackage extends AntClientToSatellitePackage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7151742592686173211L;
	private Point desiredMoveToPoint;
	private Point initialPoint;
	int antId;

	public AntMovementPackage(NodeId id, int antId, Point startPoint, Point nextPoint)
	{
		super(id);

		this.desiredMoveToPoint = nextPoint;
		this.antId = antId;
		this.initialPoint = startPoint;
	}

	public Point getDesiredMoveToPoint()
	{
		return desiredMoveToPoint;
	}

	public void setDesiredMoveToPoint(Point desiredMoveToPoint)
	{
		this.desiredMoveToPoint = desiredMoveToPoint;
	}

	public int getAntId()
	{
		return antId;
	}

	public void setAntId(int antId)
	{
		this.antId = antId;
	}

	public Point getInitialPoint()
	{
		return initialPoint;
	}	
	
	public String toString()
	{
		return "Ant Movement Package: Ant " + antId + " requests move from " + initialPoint.x + " " + initialPoint.y + " to " + desiredMoveToPoint.x + " " + desiredMoveToPoint.y;
	}
}
