package de.client.company;

import java.util.ArrayList;

import de.shared.game.Constants;
import de.shared.map.region.ResourceType;

public class Warehouse extends Department {
	
	private ArrayList<Ware> ware = new ArrayList<Ware>();
	
	public Warehouse(){
		super("Lagerhaus", 0);
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
	
	@Override
	public void calculateCosts() {
		// assumption: every ware produces the same costs for storing
		double storedAmount = 0;
		for(Ware tmpWare: ware){
			storedAmount += tmpWare.getAmount();
		}
		this.setCosts(storedAmount * Constants.STORING_COSTS);
	}

}
