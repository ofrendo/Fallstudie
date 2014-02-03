package de.shared.map.region;

import java.io.Serializable;

public enum ResourceType implements Serializable {
	COAL(false, 
			60 * 90 * 24, //produktion
			0.5, //adjustabiltiy
			99000, //pconsumption
			480000000, //pPurchaseValue
			17000000, //pMaxRunningCosts
			4, //mBuildTime
			15, //pDepreciationYears
			99000, //mMaxProduction
			100000000, //mPurchaseValue,
			5500000, //mMaxRunningCosts, 
			2, //mBuildTime,
			15, //mDepreciationYears,
			7920000), //mDefaultResourceAmount
	GAS(false, 
			30 * 90 * 24,
			0.05,
			1260000,
			150000000,
			8000000,
			2, 
			15,
			1260000,
			200000000,
			3500000,
			4,
			15,
			100800000),
	URANIUM(false,
			150 * 90 * 24,
			1, 
			10,
			3100000000.0,
			5000000,
			10, 
			20, 
			10, 
			15000000,
			4000000,
			3, 
			15, 
			800),
	WIND(true, 8 * 90 * 24, 1, -1, 144000000, 1000000, 2, 15, 0, 0, 0, 0, 0, 0.0),
	SOLAR(true, 3 * 90 * 24, 1, -1, 45000000, 700000, 1, 15, 0, 0, 0, 0, 0, 0.0),
	WATER(true, 15 * 90 * 24, 0.05, -1, 180000000, 2500000, 6, 15, 0, 0, 0, 0, 0, 0.0),
	
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
