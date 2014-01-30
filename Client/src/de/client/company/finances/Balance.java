package de.client.company.finances;

import java.util.ArrayList;

import de.client.company.Building;
import de.client.company.Company;
import de.shared.game.Constants;
import de.shared.map.region.ResourceRegion;

public class Balance {
	private Company company;
	private ProfitAndLoss profitAndLoss;
	
	private double equity = Constants.START_MONEY;
	private double debtCapital = 0;
	private double buildingsValue = 0;
	private double plotValue = 0;
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
		// update money
		money = company.getMoney();
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
		company.getClient().getClientGame().getPlayer();
		ArrayList<ResourceRegion> regions = company.getClient().getClientGame().getMap().getOwnedResourceRegions(company.getClient().getClientGame().getPlayer());
		for(ResourceRegion region: regions){
			plotValue += region.winningBid;
		}
		// update inventory value
		inventoryValue = company.getWarehouse().getStoredValue();
	}
	
	public ProfitAndLoss getProfitAndLoss(){
		return profitAndLoss;
	}
	
	public double getAssetsSum(){
		return getPlotValue()
			+ getBuildingsValue()
			+ getInventoryValue()
			+ getMoneyValue();
	}
	
	public double getLiabilitiesSum(){
		return getEquity()
			+ getDebtCapital();
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
