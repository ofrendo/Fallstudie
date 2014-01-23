package de.client.company.finances;

import java.util.ArrayList;

import de.client.company.*;

public class ProfitAndLoss {
	private Company company;
	
	// values for calculation
	private double revenue = 0;				// UefeE
	private double departmentCosts = 0;		// Aufwendungen für Departments (Löhne, Gehälter, ...)
	private double financeCosts = 0;		// Zinsaufwendungen
	private double depreciation = 0;		// Aufwendungen für Abschreibungen
	private double profitBeforeTaxes = 0;
	private double taxes = 0;
	private double profitNet = 0;
	
	// values for next calculation
	private double nextRevenue = 0;
	private double nextFinanceCosts = 0;
	private double nextDepartmentCosts = 0;
	
	public ProfitAndLoss(Company company){
		this.company = company;
	}
	
	public void nextRound(){
		nextRevenue += 0;										// REVENUE MUSS NOCH HINZUGEFÜGT WERDEN
		ArrayList<Credit> credits = company.getFinances().getCredits();
		for(Credit credit: credits){
			nextFinanceCosts += credit.getInterestsPerQuarter();
		}
		nextDepartmentCosts += company.getWarehouse().getCosts() + company.getFinances().getCosts();
	}
	
	public void nextYear(){
		// get cumulated variables for the past 4 quarters
		revenue = nextRevenue;
		financeCosts = nextFinanceCosts;
		departmentCosts = nextDepartmentCosts;
		// reset those variables 
		nextRevenue = 0;
		nextFinanceCosts = 0;
		nextDepartmentCosts = 0;
		// recalculate depreciation
		depreciation = 0;
		ArrayList<Building> buildings = company.getBuildings();
		for(Building building: buildings){
			depreciation += building.getLastDepreciation();
		}
		// recalculate profit
		profitBeforeTaxes = 
				+ getRevenue() 
				+ getInventoryChange() 
				- getDepreciation() 
				- getDepartmentCosts() 
				- getFinanceCosts();
		// recalculate taxes
		// 15 % Körperschaftssteuer + 3,5 % * Hebesatz Gewerbesteuer (Hebesatz durchschnittlich 390 %)
		// = 28,65 % => ~ 30 %;
		if(profitBeforeTaxes > 0){
			taxes = profitBeforeTaxes * 0.3;
		} else {
			taxes = 0;
		}
		// pay taxes
		company.setMoney(company.getMoney() - taxes);
		// recalculate net profit
		profitNet = profitBeforeTaxes - taxes;
	}
	
	public double getRevenue(){
		return revenue;
	}
	
	public double getInventoryChange(){
		return company.getWarehouse().getInventoryChange();
	}
	
	public double getDepreciation(){
		return depreciation;
	}
	
	public double getDepartmentCosts(){
		return departmentCosts;
	}
	
	public double getFinanceCosts(){
		return financeCosts;
	}
	
	public double getProfitBeforeTaxes(){
		return profitBeforeTaxes;
	}
	
	public double getTaxes(){
		return taxes;
	}
	
	public double getProfitNet(){
		return profitNet;
	}
}