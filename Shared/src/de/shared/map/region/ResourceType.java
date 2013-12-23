package de.shared.map.region;

import java.io.Serializable;

public enum ResourceType implements Serializable {
	COAL(10, 0.5, 1, 100000, 10000, 4),
	GAS(50, 0.05, 1, 200000, 10000, 4),
	URANIUM(100, 1, 1, 500000, 50000, 8),
	WIND(5, 1, -1, 50000, 5000, 2),
	SOLAR(10, 1, -1, 100000, 5000, 4),
	WATER(1, 0.05, -1, 200, 20, 1),
	EMPTY(0, 0, 0, 0, 0, 0),
	
	TEST_20(20, 0.2, -1, 100000, 10000, 0),
	TEST_30(30, 0.2, -1, 100000, 10000, 0),
	TEST_40(40, 0.2, -1, 90000, 10000, 0);
	
	public double pMaxProduction;
	public double pAdjustability;
	public int pConsumption;
	public double pPurchaseValue;
	public double pMaxRunningCosts;
	public int pBuildTime;
	public int pDepreciationYears;
	
	
	ResourceType(double pMaxProduction, double pAdjustability, int pConsumption, double pPurchaseValue, double pMaxRunningCosts, int pBuildTime) {
		this.pMaxProduction = pMaxProduction;
		this.pAdjustability = pAdjustability;
		this.pConsumption = pConsumption;
		this.pPurchaseValue = pPurchaseValue;
		this.pMaxRunningCosts = pMaxRunningCosts;
		this.pBuildTime = pBuildTime;
		this.pDepreciationYears = Integer.MAX_VALUE; //How is this calculated?
	}
	
	
}
