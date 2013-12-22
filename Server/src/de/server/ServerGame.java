package de.server;

import java.util.ArrayList;

import de.server.message.MessagePlayerReadyChange;
import de.shared.game.Game;
import de.shared.game.GamePhase;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.region.*;
import de.shared.map.relation.Contract;
import de.shared.map.relation.ContractRequest;
import de.shared.map.relation.ContractRequestAnswer;

public class ServerGame extends Game {
	
	public boolean isPinging = false;

	private int minimumPlayers = 2;
	
	private ArrayList<ResourceRegionBid> currentBids;
	
	public ServerGame() {
		super();
		currentBids = new ArrayList<ResourceRegionBid>();
		map = Map.getInstance();
	}
	
	public synchronized void pingPlayerReady() {
		isPinging = true;
		System.out.println("[SERVER] A player is ready.");
		Server.getInstance().sendBroadcastMessage(new MessagePlayerReadyChange(players));
		
		if (this.isAllPlayersReady() && (players.size() >= minimumPlayers || gamePhase == GamePhase.GAME_STARTED)) {
			if (gamePhase == GamePhase.PLAYERS_JOINING) {
				nextGamePhase();
				setAllPlayersUnready();
				Server.getInstance().startGame();
			}
			else if (gamePhase == GamePhase.GAME_STARTED) {
				finishRound();
			}
		}
		isPinging = false;
	}
	
	public synchronized boolean isAllPlayersReady() {
		for (Player player : players) {
			if (player.ready == false) 
				return false;
		}
		return true;
	}
	
	public synchronized void setAllPlayersUnready() {
		for (Player player : players) {
			player.ready = false;
		}
	}
	
	public synchronized void finishRound() {
		handleBids();
		
		setAllPlayersUnready();
		System.out.println("[SERVER] Finishing round...");
		incrementRound();
		
		Server.getInstance().sendMapUpdate();
		Server.getInstance().sendBroadcastMessage(new MessagePlayerReadyChange(players)); //is this needed?
	}
	
	public synchronized void handleBids() {
		for (Region region : map.getRegions()) {
			
			if (region instanceof ResourceRegion) {
				ResourceRegion resourceRegion = (ResourceRegion) region;
				
				double highestBid = 0;
				Player highestPlayer = null;
				
				for (ResourceRegionBid bid : currentBids) {
					if (bid.coords.equals(resourceRegion.coords) && highestBid < bid.amount) {
						highestBid = bid.amount;
						highestPlayer = bid.player;
					}
				}
				
				if (highestPlayer != null && highestBid > 0 && resourceRegion.resourceRegionStatus == ResourceRegionStatus.BUYABLE) {
					resourceRegion.owner = highestPlayer;
					resourceRegion.resourceRegionStatus = ResourceRegionStatus.OWNED;
				}
			}
			
		}
		
		currentBids.clear();
	}

	public synchronized void setRegionBiddable(Coords regionCoords) {
		for (Region region : map.getRegions()) {
			
			if (region instanceof ResourceRegion && region.coords.equals(regionCoords)) {
				ResourceRegion resourceRegion = (ResourceRegion) region;
				
				if (!resourceRegion.resourceType.equals(ResourceType.EMPTY) && 
					resourceRegion.resourceRegionStatus == ResourceRegionStatus.NEUTRAL) {
					//System.out.println("Changing regionStatus");
					resourceRegion.resourceRegionStatus = ResourceRegionStatus.BUYABLE;
					
				}
				
			}
			
		}
	}

	public synchronized void addRegionBid(ResourceRegionBid regionBid) {
		currentBids.add(regionBid);
	}
	
	public Contract calculateContract(ContractRequest request) {
		CityRegion cityRegion = (CityRegion) getMap().getRegion(request.coords);
		int freeCustomers = cityRegion.getFreeCustomers();
		
		int averagePriceCustomers = (int) (freeCustomers * request.awareness * request.popularity);
		double averagePrice = map.getEnergyExchange().getCurrentEnergyPrice();
		
		//int customersForClient = (maxAvailableCustomers > request.maxCustomers) ? request.maxCustomers : maxAvailableCustomers;
		int customersForClient = customerFunction(freeCustomers, averagePriceCustomers, averagePrice, request.amountMoneyPerCustomer);
		
		if (customersForClient > request.maxCustomers)
			customersForClient = request.maxCustomers;
		
		double cityDemand = customersForClient * getMap().getEnergyFactor();
		double amountMoneyPerCustomer = request.amountMoneyPerCustomer; //CHANGE THIS, atm you could set it to whatever price you wanted
		
		Contract contract = new Contract(customersForClient, cityDemand, amountMoneyPerCustomer);
		return contract;
	}

	private int customerFunction(int freeCustomers, int averagePriceCustomers, double averagePrice, double price) {
		/**
		 * Zwei Parabeln: eine von 0 bis zum preis danach von 0 bis customer = 0;
		 * Parabel in der Scheitelpunktform f(x) = a(x-d)^2 + b
		 */
		int b = averagePriceCustomers;
		double a = (b+freeCustomers) / averagePrice;
		double d = averagePrice;
		double x = price;
		
		if (x > d) a = -a;
		
		int result = (int) (a * (x-d) * (x-d) + b);
		if (result < 0) result = 0;
		
		return result;
	}
	
	public synchronized void confirmContract(ContractRequestAnswer confirmation) {
		CityRegion cityRegion = (CityRegion) getMap().getRegion(confirmation.coords);
		cityRegion.setFreeCustomers(cityRegion.getFreeCustomers() - confirmation.contract.amountCustomer);
	}
	
	public synchronized void cancelContract(ContractRequestAnswer cancellation) {
		CityRegion cityRegion = (CityRegion) getMap().getRegion(cancellation.coords);
		cityRegion.setFreeCustomers(cityRegion.getFreeCustomers() + cancellation.contract.amountCustomer);
	}
	
	//METHOD FOR TESTING
	public ResourceRegion getFirstNeutralResourceRegion() {
		for (Region region : map.getRegions()) {
			if (region instanceof ResourceRegion) {
				ResourceRegion r = (ResourceRegion) region;
				if (r.resourceRegionStatus == ResourceRegionStatus.NEUTRAL && r.resourceType != ResourceType.EMPTY) {
					//System.out.println(r.resourceType);
					return r;
				}
			}
		}
		return null;
	}

	//METHOD FOR TESTING
	public CityRegion getFirstCityRegion() {
		for (Region region : map.getRegions()) {
			if (region instanceof CityRegion) {
				CityRegion r = (CityRegion) region;
				return r;
			}
		}
		return null;
	}
	
}
