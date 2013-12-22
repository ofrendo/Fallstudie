package de.shared.map.region;

public class CityRegion extends Region {

	private static final long serialVersionUID = 5740457263191994777L;
	
	private int population;
	private int freeCustomers;
	private double environmentalAwareness;
	
	public CityRegion(int regionIDX, int regionIDY, int population) {
		super(regionIDX, regionIDY);
		this.population = population;
		this.freeCustomers = population;
		this.environmentalAwareness = Math.random();
	}
	
	public int getPopulation() {
		return population;
	}
	
	public int getFreeCustomers() {
		return freeCustomers;
	}
	
	public synchronized void setFreeCustomers(int n) {
		this.freeCustomers = n;
	}
	
	public double getEnvironmentalAwareness() {
		return environmentalAwareness;
	}
	
}
