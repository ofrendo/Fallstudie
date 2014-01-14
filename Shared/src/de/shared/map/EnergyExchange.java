package de.shared.map;

import java.io.Serializable;
import java.util.ArrayList;

import de.shared.game.Constants;

public class EnergyExchange implements Serializable {

	private static final long serialVersionUID = -6346571442591481630L;
	
	private double demand = 0;			// these variables are for the demand and offer for the players
	private double offer = 0;			//
	
	private double globalDemand = Constants.START_GLOBAL_ENERGY_DEMAND;	// these variables are for the demand and offer from other nonplayer companies
	private double globalOffer =  Constants.START_GLOBAL_ENERGY_OFFER;	// (just random values to simulate a real market; players just participate in this market)
	
	private double price;
	
	private ArrayList<Double> priceHistory;

	public EnergyExchange(){
		this.calculatePrice();
		this.priceHistory = new ArrayList<Double>();
	}
	
	public void calculatePrice(){	// should only be done on serverside
		price = Constants.NORMAL_ENERGY_PRICE * (globalDemand + demand) / (globalOffer + offer);
	}
	
	private void addDemand(double amount){
		this.demand += amount;
	}
	
	private void addOffer(double amount){
		this.offer += amount;
	}
	
	/**
	 * Needs to be called once every round by the server.
	 */
	public void nextGlobalValues(){
		globalDemand *= (1 + (0.5 - Math.random()) * 0.1);
		globalOffer *= (1 + (0.5 - Math.random()) * 0.1);
		
		calculatePrice();	
		// store price in history
		this.priceHistory.add(this.getCurrentEnergyPrice());
		// reset demand and offer for next quartal
		demand = 0;
		offer = 0;
	}

	public void addTrade(double amountEnergy) {
		if (amountEnergy > 0) {
			addOffer(amountEnergy);
		}
		else {
			addDemand(- amountEnergy);
		}
	}
	
	public double getCurrentEnergyPrice() {
		return price;
	}
	
	public double getPrice(double amount){
		return this.price * amount; 
	}
	
	public ArrayList<Double> getPriceHistory() {
		return priceHistory;
	}
	
}
