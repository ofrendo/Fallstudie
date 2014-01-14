package de.client.company;

import java.util.ArrayList;

import de.shared.game.Constants;
import de.shared.map.relation.RegionRelation;

public class Balance {
	private Company company;
	private ProfitAndLoss profitAndLoss;
	
	private double equity = Constants.START_MONEY;
	private double debtCapital = 0;
	private double buildingsValue = 0;
	private double plotValue;
	private double money = Constants.START_MONEY;
	private double inventoryValue = 0;
	
	public Balance(Company company){
		this.company = company;
		this.profitAndLoss = new ProfitAndLoss(company);
	}
	
	public void nextRound(){
		profitAndLoss.nextRound();
	}
	
	public void nextYear(){
		// update profit and loss
		profitAndLoss.nextYear();
		// update equity
		equity += profitAndLoss.getProfitNet();
		// update debt capital
		debtCapital = company.getFinances().getDebtCapital();
		// update book value of the buildings
		buildingsValue = 0;
		ArrayList<Building> buildings = company.getBuildings();
		for(Building building: buildings){
			buildingsValue += building.getBookValue();
		}
		// update plot value
		plotValue = 0;
		ArrayList<RegionRelation> regionRelations = company.getRegionRelations();
		for(RegionRelation regionRelation: regionRelations){
			// 											PLOTVALUE MUSS NOCH HINZUGEFÜGT WERDEN
			// plotValue += ????????????????????????
		}
		// update inventory value
		company.getWarehouse().getStoredValue();
	}
	
	public ProfitAndLoss getProfitAndLoss(){
		return profitAndLoss;
	}
	
	public double getBuildingsValue(){
		return buildingsValue;
	}
	
	public double getPlotValue(){
		return plotValue;
	}
	
	public double getInventoryValue(){
		return inventoryValue;
	}
	
	public double getEquity(){	// Eigenkapital
		return equity;
	}
	
	public double getMoneyValue(){
		return money;
	}
	
	public double getDebtCapital(){
		return debtCapital;
	}
}
