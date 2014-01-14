package de.client.company;

public abstract class Department {
	private String name;
	private double costs;
	private Company company;
	
	public Department(String name, double costs, Company company){
		this.name = name;
		this.costs = costs;
		this.company = company;
	}
	
	public Company getCompany(){
		return company;
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

	public abstract void nextRound();
}
