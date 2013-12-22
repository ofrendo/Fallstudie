package de.client.company;

public class Building {
	private double productionMax;
	private double utlizationRate = 1;
	
	public Building(double productionMax) {
		this.productionMax = productionMax;
	}
	
	public double getProduction() {
		return productionMax * utlizationRate;
	}
}
