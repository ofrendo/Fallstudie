package de.shared.map.region;

import java.io.Serializable;

public enum ResourceType implements Serializable {
	COAL(false, 600 * 90 * 24, 0.5, 1, 100000, 10000, 4, 30, 10, 50000, 5000, 4, 4, 1000.0),
	GAS(false, 300 * 90 * 24, 0.05, 1, 200000, 10000, 4, 20, 5, 25000, 3000, 4, 4, 500.0),
	URANIUM(false, 1500 * 90 * 24, 1, 1, 500000, 50000, 8, 35, 10, 100000, 10000, 5, 5, 1000.0),
	WIND(true, 80 * 90 * 24, 1, -1, 50000, 5000, 2, 16, 0, 0, 0, 0, 0, 0.0),
	SOLAR(true, 30 * 90 * 24, 1, -1, 100000, 5000, 4, 20, 0, 0, 0, 0, 0, 0.0),
	WATER(true, 150 * 90 * 24, 0.05, -1, 200, 20, 1, 40, 0, 0, 0, 0, 0, 0.0),
	
	EMPTY(false, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0.0),
	
	TEST_20(true, 20, 0.2, -1, 100000, 10000, 0, 0, 0, 0, 0, 0, 0, 0.0),
	TEST_30(true, 30, 0.2, -1, 100000, 10000, 0, 0, 0, 0, 0, 0, 0, 0.0),
	TEST_40(true, 40, 0.2, -1, 90000, 10000, 0, 0, 0, 0, 0, 0, 0, 0.0);
	
	public boolean isRenewable;
	
	// Values for PowerStations
	public double pMaxProduction;
	public double pAdjustability;
	public int pConsumption;
	public double pPurchaseValue;
	public double pMaxRunningCosts;
	public int pBuildTime;
	public int pDepreciationYears;
	// Values for Mines
	public double mMaxProduction;
	public double mPurchaseValue;
	public double mMaxRunningCosts;
	public int mBuildTime;
	public int mDepreciationYears;
	
	public double mDefaultResourceAmount; 
	
	ResourceType(
			boolean isRenewable,
			double pMaxProduction, 
			double pAdjustability, 
			int pConsumption, 
			double pPurchaseValue, 
			double pMaxRunningCosts, 
			int pBuildTime, 
			int pDepreciationYears, 
			double mMaxProduction, 
			double mPurchaseValue,
			double mMaxRunningCosts, 
			int mBuildTime,
			int mDepreciationYears,
			double mDefaultResourceAmount) {
		
		this.isRenewable = isRenewable;
		
		this.pMaxProduction = pMaxProduction;
		this.pAdjustability = pAdjustability;
		this.pConsumption = pConsumption;
		this.pPurchaseValue = pPurchaseValue;
		this.pMaxRunningCosts = pMaxRunningCosts;
		this.pBuildTime = pBuildTime;
		this.pDepreciationYears = pDepreciationYears;
		
		this.mMaxProduction = mMaxProduction;
		this.mPurchaseValue = mPurchaseValue;
		this.mMaxRunningCosts = mMaxRunningCosts;
		this.mBuildTime = mBuildTime;
		this.mDepreciationYears = mDepreciationYears;
		this.mDefaultResourceAmount = mDefaultResourceAmount;
	}
	
	
}
