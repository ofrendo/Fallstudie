package de.shared.map;

import java.io.Serializable;

public class EnergyExchange implements Serializable {

	private static final long serialVersionUID = -6346571442591481630L;
	
	private int demand = 0;			// these variables are for the demand and offer for the players
	private int offer = 0;			//
	
	private int globalDemand = 100;	// these variables are for the demand and offer from other nonplayer companies
	private int globalOffer = 100;	// (just random values to simulate a real market; players just participate in this market)
	
	private double price;

	
	public void calculatePrice(){
		price = (globalDemand + demand) / (globalOffer + offer);
	}
	
	public void changeGlobalValues(){
		globalDemand *= (1 + (0.5 - Math.random()) * 0.1);
		globalOffer *= (1 + (0.5 - Math.random()) * 0.1);
	}
	
	public void buy(int amount){
		this.demand += amount;
	}
	
	public void sell(int amount){
		this.offer += amount;
	}
	
	public double getCurrentEnergyPrice() {
		return price;
	}
	
	public double getPrice(int amount){
		return this.price * amount; 
	}
	
}
