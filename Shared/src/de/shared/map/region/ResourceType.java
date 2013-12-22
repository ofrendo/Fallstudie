package de.shared.map.region;

import java.io.Serializable;


public enum ResourceType implements Serializable {
	COAL(10),
	GAS(20),
	URANIUM(100),
	WIND(5),
	SOLAR(5),
	WATER(1),
	EMPTY(0);
	
	public double maxProduction;
	
	ResourceType(double maxProduction) {
		this.maxProduction = maxProduction;
	}
	
	
}
