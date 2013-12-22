package de.client.company;

import de.shared.map.relation.*;
import de.shared.map.region.*;

public class ResourceRelation extends RegionRelation {
	private PowerStation powerStation = null;
	private Mine mine = null;
	
	public ResourceRelation(Coords coords) {
		super(coords);
	}

	public Mine getMine() {
		return mine;
	}

	public void setMine(Mine mine) {
		this.mine = mine;
	}

	public PowerStation getPowerStation() {
		return powerStation;
	}

	public void setPowerStation(PowerStation powerStation) {
		this.powerStation = powerStation;
	}
	
}
