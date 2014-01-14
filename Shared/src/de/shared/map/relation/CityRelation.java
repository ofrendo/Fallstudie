package de.shared.map.relation;

import de.shared.map.region.*;
import de.shared.game.Constants;

public class CityRelation extends RegionRelation {

	//public CityRelation() { }
	//private Contract contract;
	
	public double popularity = Constants.START_POPULARTIY;
	public double awareness = Constants.START_AWARENESS;
	
	public CityRelation(Coords coords) {
		super(coords);
	}
	
	/*public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}*/

}
