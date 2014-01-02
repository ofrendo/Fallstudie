package de.client.company;

import de.shared.map.region.ResourceType;

public class Mine extends Building {
	
	private ResourceType resourceType;
	
	public Mine(ResourceType resourceType) {
		super(resourceType.mMaxProduction, resourceType.mPurchaseValue, 
				resourceType.mDepreciationYears, resourceType.mBuildTime);
		this.resourceType = resourceType;
	}

	public double getRunningCosts() {
		return resourceType.mMaxRunningCosts * utilizationRate;
	}
	
	public double getProduction() {
		return resourceType.mMaxProduction * utilizationRate;
	}
	
}
