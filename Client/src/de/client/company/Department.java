package de.client.company;

public abstract class Department {
	private String name;
	private double costs;
	
	public Department(String name, double costs){
		this.name = name;
		this.costs = costs;
	}
	
	public double getCosts() {
		return costs;
	}

	public void setCosts(double costs) {
		this.costs = costs;
	}

	public String getName() {
		return name;
	}

	public abstract void calculateCosts();
}
