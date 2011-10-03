package communication;

import java.io.Serializable;

import satellite.SatelliteEngine;
import client.Ant;
import engine.Block;
import engine.Grid;

/**
 * Baseline class for all packages; meant to be overridden
 * by specific packages, such as initialization, movement, etc.
 * 
 * @author DeepBlue
 *
 */
public abstract class AbstractPackage implements Serializable
{
	private static final long serialVersionUID = -8949599953951279490L;
	private int uniqueId;
	
	public abstract void initialize(Ant ant);
	
	public abstract void updateGrid(Grid mainGrid);
	
	public abstract void processReturn(Ant a);

	public int getUniqueId() 
	{
		return uniqueId;
	}

	public void setUniqueId(int uniqueId) 
	{
		this.uniqueId = uniqueId;
	}
	
	
	
}
