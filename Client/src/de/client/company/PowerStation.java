package de.client.company;

import java.util.ArrayList;

import de.shared.map.relation.CityRelation;

public class PowerStation extends Building {

	private ArrayList<PowerStationRelation> powerStationRelations;
	
	public PowerStation(double productionMax) {
		super(productionMax);
		
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
	
}
