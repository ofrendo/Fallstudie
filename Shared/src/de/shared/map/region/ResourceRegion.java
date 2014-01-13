package de.shared.map.region;

import de.shared.game.Player;

public class ResourceRegion extends Region {

	private static final long serialVersionUID = 1554530866898580031L;
	
	public ResourceType resourceType;
	public ResourceRegionStatus resourceRegionStatus;
	
	public Player owner;
	public double price; //need to set this differently after buying on serverside
	public double resourceAmount;
	
	public ResourceRegion(int regionIDX, int regionIDY, ResourceType resourceType) {
		super(regionIDX, regionIDY);
		this.resourceType = resourceType;
		this.resourceRegionStatus = ResourceRegionStatus.NEUTRAL;
		
		//get random factor for resource amount
		double random = (Math.random()/2.0)+0.75;
		
		this.resourceAmount =  (random*resourceType.mDefaultResourceAmount);
	
	}
	
	public void setResourceRegionStatus(ResourceRegionStatus status) {
		//region is owned and one building ist build
		if (resourceRegionStatus == ResourceRegionStatus.OWNED) {
			resourceRegionStatus = status;
		}
		//if one building is already finished
		else if((resourceRegionStatus == ResourceRegionStatus.MINE && 
				status == ResourceRegionStatus.POWERSTATION) ||
				(resourceRegionStatus == ResourceRegionStatus.POWERSTATION &&
				status == ResourceRegionStatus.MINE))
			
			resourceRegionStatus = ResourceRegionStatus.MINE_POWERSTATION;
			
			
	}

}
