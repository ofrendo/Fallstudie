package de.shared.map.region;

import de.shared.game.Player;

public class ResourceRegion extends Region {

	private static final long serialVersionUID = 1554530866898580031L;
	
	public ResourceType resourceType;
	public ResourceRegionStatus resourceRegionStatus;
	
	public Player owner;
	public double price; //need to set this differently after buying on serverside
	public int resourceAmount;
	
	public ResourceRegion(int regionIDX, int regionIDY, ResourceType resourceType) {
		super(regionIDX, regionIDY);
		this.resourceType = resourceType;
		this.resourceRegionStatus = ResourceRegionStatus.NEUTRAL;
	}

}
