package de.client.company;

import java.util.ArrayList;

import de.shared.map.region.ResourceType;
import de.shared.map.relation.CityRelation;

public class PowerStation extends Building {

	private ArrayList<PowerStationRelation> powerStationRelations;
	
	private final double adjustability;
	public double maintenanceRate = 1;
	private final int consumption;
	
	private ResourceType resourceType;
	
	public PowerStation(ResourceType resourceType) {
		super(resourceType.pMaxProduction, resourceType.pPurchaseValue, resourceType.pDepreciationYears, resourceType.pBuildTime);
		this.adjustability = resourceType.pAdjustability;
		this.consumption = resourceType.pConsumption;
		this.powerStationRelations = new ArrayList<PowerStationRelation>();
		this.resourceType = resourceType;
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

	public double getRunningCosts() {
		return (resourceType.pMaxRunningCosts * utilizationRate + resourceType.pMaxRunningCosts * maintenanceRate) / 2;
	}

	public int getConsumption() {
		return (int) (utilizationRate * consumption);
	}
	
}
