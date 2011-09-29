package communication;

import java.awt.Point;

import engine.BlockSnapshot;

public interface Portal 
{
	public void dispatchPackage(AbstractPackage aPackage);
	
	public void processClientPackage(AbstractPackage aPackage);
	
	public void processServerPackage(AbstractPackage aPackage);
	
	public void addBlockSnapshot(Point blockLocation, BlockSnapshot snapshot);
}
