package de.client.company;

import java.util.ArrayList;

public class Balance {
	private Company company;
	
	public Balance(Company company){
		this.company = company;
	}
	
	public void calculateBalance(){
		
	}
	
	
	public double getBuildingsValue(){
		double value = 0;
		ArrayList<Building> buildings = this.company.getBuildings();
		for(Building building: buildings){
			value += building.getBookValue();
		}
		return value;
	}
}
