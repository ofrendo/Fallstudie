package de.shared.game;

/**
 * Used for static constants in the game so that they are the same everywhere.
 */
public class Constants {
	public static final double ENERGY_FACTOR = 0.4;
	
	public static double START_POPULARTIY = 0.5;
	public static double START_AWARENESS = 0.5;
	public static double START_MONEY = 650000000;
	
	public static int MAX_POWERSTATION_DISTANCE = 3;
	
	//Costs and prices
	public static double NET_USAGE_COSTS = 6;
	public static double NORMAL_ENERGY_PRICE = 200; //Normal energy price: this is the price when demand equals offers 
	
	//Resource Costs
	public static double COST_COAL = 70;
	public static double COST_GAS = 3;
	public static double COST_URANIUM = 250000;
	public static double MINIMUM_REGION_BID = 10000000;
	
	// Storing costs for the warehouse per buying costs
	public static double STORING_COSTS_RATIO = 0.01;
	
	
	//Start values for energy exchange
	public static double START_GLOBAL_ENERGY_DEMAND = 10000000;
	public static double START_GLOBAL_ENERGY_OFFER = 10000000;
	
	//EventCosts
	public static double MAINTENANCE_ACCIDENT_SMALL = 0.05;
	public static double MAINTENANCE_ACCIDENT_MEDIUM = 0.15;
	public static double MAINTENANCE_ACCIDENT_LARGE = 0.30; //with fries ;)
	
	//Server constants for ending a game
	public static double GAME_END_MIN_ROUNDS = 50;
	public static double GAME_END_MIN_MARKETSHARE = 0.5;
	
}
