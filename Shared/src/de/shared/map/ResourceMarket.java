package de.shared.map;

import java.io.Serializable;

import de.shared.game.Constants;
import de.shared.map.region.ResourceType;

public class ResourceMarket implements Serializable {

	private static final long serialVersionUID = 712884594137333842L;

	public double getResourcePrice(ResourceType resourceType) {
		switch(resourceType) {
		case COAL: 
			return Constants.COST_COAL;
		case GAS:
			return Constants.COST_GAS;
		case URANIUM:
			return Constants.COST_URANIUM;
		default:
			return -1;
		}
	}
	
	public void buyResource(BuyableResource buyableResource) {
		
	}
	
	public void sellResource(BuyableResource buyableResource) {
		
	}
	
}
