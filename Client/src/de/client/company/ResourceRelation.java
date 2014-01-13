package de.client.company;

import de.shared.map.relation.*;
import de.shared.map.region.*;

public class ResourceRelation extends RegionRelation {
	
	public PowerStation powerStation = null;
	public Mine mine = null;
	public int resourceAmount;
	
	public ResourceRelation(Coords coords, int resourceAmount) {
		super(coords);
		this.resourceAmount = resourceAmount;
	}

	public Mine getMine() {
		return mine;
	}

	public void setMine(Mine mine) {
		this.mine = mine;
	}
	
	public void decreaseResourceAmount(int amount) {
		resourceAmount -= amount;
	}

	public PowerStation getPowerStation() {
		return powerStation;
	}

	public void setPowerStation(PowerStation powerStation) {
		this.powerStation = powerStation;
	}
	
}
