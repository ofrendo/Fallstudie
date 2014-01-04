package de.shared.map;

import de.shared.map.region.ResourceType;

public enum BuyableResource {
	ENERGY("Strom", "MWh", null),
	COAL("Kohle", "t", ResourceType.COAL),
	GAS("Gas", "m^3", ResourceType.GAS),
	URANIUM("Uran", "t", ResourceType.URANIUM);
	
	public String name;
	public String unit;
	public ResourceType resourceType;
	
	BuyableResource(String name, String unit, ResourceType resourceType) {
		this.name = name;
		this.unit = unit;
		this.resourceType = resourceType;
	}
	
}
