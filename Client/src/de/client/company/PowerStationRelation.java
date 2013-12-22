package de.client.company;

import de.shared.map.relation.RegionRelation;
import de.shared.map.region.*;

public class PowerStationRelation extends RegionRelation {

	//public PowerStationRelation() { }
	
	public PowerStationRelation(Coords coords) {
		super(coords);
	}
	
	public PowerStationRelation(RegionRelation otherRelation) {
		super(otherRelation.coords);
	}
	
	/**
	 * Value between 0 and 1, 1 means all of the powerstation production is going towards this city
	 */
	public double partPowerStationProduction = 0; 
		
}
