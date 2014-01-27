package de.client.company;

import de.shared.map.relation.*;
import de.shared.map.region.*;

public class ResourceRelation extends RegionRelation {
	
	public PowerStation powerStation = null;
	public Mine mine = null;
	public double resourceAmount;
	
	public double bid;
	
	public ResourceRelation(Coords coords, double resourceAmount) {
		super(coords);
		this.resourceAmount = resourceAmount;
		//System.out.println("ResourceRelation" +resourceAmount);
	}

	public Mine getMine() {
		return mine;
	}

	public void setMine(Mine mine) {
		this.mine = mine;
	}
	
	public void decreaseResourceAmount(double amount) {
		resourceAmount -= amount;
	}

	public PowerStation getPowerStation() {
		return powerStation;
	}

	public void setPowerStation(PowerStation powerStation) {
		this.powerStation = powerStation;
	}
	
}
