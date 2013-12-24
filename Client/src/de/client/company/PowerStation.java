package de.client.company;

import java.util.ArrayList;

import de.shared.map.relation.CityRelation;
import de.shared.map.region.*;

public class PowerStation extends Building {

	private ArrayList<PowerStationRelation> powerStationRelations;
	
	private final double adjustability;
	private double maintenanceRate = 1;
	private double maxRunningCosts;
	private double runningCosts;
	private final int consumption;
	
	public PowerStation(ResourceType resourceType) {
		super(resourceType.pMaxProduction, resourceType.pPurchaseValue, resourceType.pDepreciationYears, resourceType.pBuildTime);
		this.adjustability = resourceType.pAdjustability;
		this.maxRunningCosts = resourceType.pMaxRunningCosts;
		this.consumption = resourceType.pConsumption;
		this.powerStationRelations = new ArrayList<PowerStationRelation>();
	}
	
	public void addPowerStationRelation(CityRelation cityRelation) {
		PowerStationRelation p = new PowerStationRelation(cityRelation.coords);
		this.powerStationRelations.add(p);
	}
	
	public void removePowerStationRelation(CityRelation cityRelation) {
		for (PowerStationRelation p : powerStationRelations) {
			if (p.compareRegionRelation(cityRelation)) {
				this.powerStationRelations.remove(p);
			}
		}
	}
	
	
	public ArrayList<PowerStationRelation> getPowerStationRelations() {
		return powerStationRelations;
	}
	
	
	
	public double getAdjustability() {
		return adjustability;
	}

	public double getMaintenanceRate() {
		return maintenanceRate;
	}

	public void setMaintenanceRate(double maintenanceRate) {
		this.maintenanceRate = maintenanceRate;
	}
	
	public void finishRound() {
		
	}
	
}
