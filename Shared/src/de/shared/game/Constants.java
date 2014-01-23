package de.shared.game;

public class Constants {
	public static double START_POPULARTIY = 0.5;
	public static double START_AWARENESS = 0.1;
	public static double START_MONEY = 5000000;	// Leute, macht euch mal Gedanken, mit wie viel "money in the tasche" man starten soll
	
	public static int MAX_POWERSTATION_DISTANCE = 3;
	
	//Costs and prices
	public static double NET_USAGE_COSTS = 0.06;
	public static double NORMAL_ENERGY_PRICE = 0.20; //Normal energy price: this is the price when demand equals offers 
	
	//Resource Costs
	public static double COST_COAL = 20;
	public static double COST_GAS = 25;
	public static double COST_URANIUM = 50;
	
	// Storing costs for the warehouse per ton
	public static double STORING_COSTS = 100;
	
	//Start values for energy exchange
	public static double START_GLOBAL_ENERGY_DEMAND = 100;
	public static double START_GLOBAL_ENERGY_OFFER = 100;
}
