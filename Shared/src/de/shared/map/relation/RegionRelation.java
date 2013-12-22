package de.shared.map.relation;

import de.shared.map.region.*;

public abstract class RegionRelation {
	public Coords coords;
	
	/*public RegionRelation() {
		
	}*/
	
	public RegionRelation(Coords coords) {
		this.coords = coords;
	}
	
	public boolean compareRegionRelation(RegionRelation otherRelation) {
		return this.coords.equals(otherRelation.coords);
	}
	
}
