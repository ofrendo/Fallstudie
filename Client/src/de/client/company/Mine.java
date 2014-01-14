package de.client.company;

import de.shared.map.region.ResourceType;

public class Mine extends Building {
	
	private ResourceType resourceType;
	private ResourceRelation relation;
	
	public Mine(ResourceType resourceType, ResourceRelation relation) {
		super(resourceType.mMaxProduction, resourceType.mPurchaseValue, 
				resourceType.mDepreciationYears, resourceType.mBuildTime);
		this.resourceType = resourceType;
		this.relation = relation;
	}

	public double getRunningCosts() {
		return resourceType.mMaxRunningCosts * utilizationRate;
	}
	
	public double getProduction() {
		double production =  resourceType.mMaxProduction * utilizationRate;
		if(production > relation.resourceAmount)
			production = relation.resourceAmount;
		return production;
	}
	
	public ResourceType getResourceType()
	{
		return resourceType;
	}
	public ResourceRelation getResourceRelation()
	{
		return this.relation;
	}
	
}
