package de.shared.map.region;

import java.io.Serializable;

public class FinishedBuilding implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Coords coords;
	public ResourceRegionStatus status;
	public FinishedBuilding(Coords coords, ResourceRegionStatus status)
	{
		this.coords = coords;
		
		if(status == ResourceRegionStatus.MINE ||
			status == ResourceRegionStatus.POWERSTATION)
			this.status = status;	
	}
	
}
