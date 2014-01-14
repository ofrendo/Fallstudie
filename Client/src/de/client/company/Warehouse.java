package de.client.company;

import java.util.ArrayList;

import de.shared.game.Constants;
import de.shared.map.region.ResourceType;

public class Warehouse extends Department {
	
	private ArrayList<Ware> ware = new ArrayList<Ware>();
	private double lastYearStoredValue = 0;
	private double inventoryChange = 0;
	
	public Warehouse(Company company){
		super("Lagerhaus", 0, company);
		addWare(ResourceType.COAL, 0);
		addWare(ResourceType.GAS, 0);
		addWare(ResourceType.URANIUM, 0);
	}
	
	public void addWare(ResourceType resourceType, double amount){
		Ware tmpWare = this.getWare(resourceType);
		if(tmpWare != null){
			tmpWare.addAmount(amount);
		} else {
			tmpWare = new Ware(resourceType, amount);
			this.ware.add(tmpWare);
		}
	}
	
	public void reduceWare(ResourceType resourceType, double amount) throws Exception{
		Ware tmpWare = this.getWare(resourceType);
		if(tmpWare != null){
			if(tmpWare.getAmount() >= amount){
				tmpWare.reduceAmount(amount);
			} else throw new Exception("Not enough ware stored for the specified resourceType");
		} else throw new Exception("No ware of the specified resourceType stored!");
	}
	
	public Ware getWare(ResourceType resourceType){
		for(Ware tmpWare: ware){
			if(tmpWare.getResourceType().equals(resourceType)){
				return tmpWare;
			}
		}
		return null;
	}
	
	public double getInventoryChange(){	// for the profit and loss calculation
		return inventoryChange;
	}
	
	public double getStoredValue(){
		double storedValue = 0;
		for(Ware tmpWare: ware){
			switch (tmpWare.getResourceType().name()) {
			case "COAL":
				storedValue += tmpWare.getAmount() * Constants.COST_COAL;
				break;
			case "GAS":
				storedValue += tmpWare.getAmount() * Constants.COST_GAS;
				break;
			case "URANIUM":
				storedValue += tmpWare.getAmount() * Constants.COST_URANIUM;
				break;
			default:
				break;
			}
		}
		return storedValue;
	}
	
	@Override
	public void nextRound() {
		// recalculate costs
		// assumption: every ware produces the same costs for storing
		double storedAmount = 0;
		for(Ware tmpWare: ware){
			storedAmount += tmpWare.getAmount();
		}
		this.setCosts(storedAmount * Constants.STORING_COSTS);
	}
	
	public void nextYear(){
		// recalc inventoryChange
		inventoryChange = lastYearStoredValue - getStoredValue();
		lastYearStoredValue = getStoredValue();
	}

}
