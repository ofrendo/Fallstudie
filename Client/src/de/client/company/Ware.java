package de.client.company;

import de.shared.map.region.ResourceType;

public class Ware {
	private ResourceType resourceType;
	private double amount;
	
	public Ware(ResourceType resourceType, double amount){
		this.resourceType = resourceType;
		this.amount = amount;
	}

	public ResourceType getResourceType() {
		return resourceType;
	}

	public double getAmount() {
		return amount;
	}
	
	public void addAmount(double amount){
		this.amount += amount;
	}
	
	public void reduceAmount(double amount){
		this.amount -= amount;
	}
	
}
