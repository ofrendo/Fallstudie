package de.shared.map;

import java.io.Serializable;

import de.shared.map.region.ResourceType;

public class ResourceMarket implements Serializable {

	private static final long serialVersionUID = 712884594137333842L;

	public double getResourcePrice(ResourceType resourceType) {
		switch(resourceType) {
		case COAL: 
			return 20;
			default: return -1;
		}
	}
	
}
