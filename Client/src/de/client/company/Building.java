package de.client.company;


public abstract class Building extends Investment {
	private double productionMax;
	public double utlizationRate = 1;
	
	private int buildingTimeLeft = Integer.MAX_VALUE;
	private boolean isBuilt = false;
	
	public Building(double productionMax, double purchaseValue, int depreciationYears, int buildingTimeLeft) {
		super(purchaseValue, depreciationYears);
		this.productionMax = productionMax;
	}
	
	public void performDepreciation(){
		if(this.isBuilt()){	// the depreciation of a building starts at the completion date
			super.performDepreciation();
		}
	}
	
	public double getProduction() {
		return productionMax * utlizationRate;
	}
	
	public void setUtilizationRate(double newRate) {
		this.utlizationRate = newRate;
	}
	
	public int getBuildingTimeLeft() {
		return buildingTimeLeft;
	}
	
	public void decrementBuildingTimeLeft() {
		buildingTimeLeft--;
		
		if (buildingTimeLeft == 0) {
			isBuilt = true;
		}
	}
	
	public boolean isBuilt() {
		return isBuilt;
	}
}
