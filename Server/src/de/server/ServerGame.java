package de.server;

import java.util.ArrayList;

import de.shared.game.Constants;
import de.shared.game.Game;
import de.shared.game.GamePhase;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.generate.MapType;
import de.shared.map.generate.MapTypeHexagon;
import de.shared.map.generate.MapTypeRect;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Coords;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceRegionBid;
import de.shared.map.region.ResourceRegionStatus;
import de.shared.map.region.ResourceType;
import de.shared.map.relation.Contract;
import de.shared.map.relation.ContractRequest;
import de.shared.message.server.MessagePlayerReadyChange;

public class ServerGame extends Game {
	
	public boolean isPinging = false;

	private int minimumPlayers = 1;
	
	private ArrayList<ResourceRegionBid> currentBids;
	
	public ServerGame() {
		super();
		currentBids = new ArrayList<ResourceRegionBid>();
		
		MapType mapType;
		if (Math.random() > 0.5) 
			mapType = MapTypeHexagon.NORMAL;
		else
			mapType = MapTypeRect.NORMAL;
		
		map = new Map(mapType);//Map.getInstance();
		
		//Set average energy prices
		double averageEnergyPrice = map.getEnergyExchange().getCurrentEnergyPrice();
		for (CityRegion cityRegion : map.getCityRegions()) {
			cityRegion.setAverageEnergyPrice(averageEnergyPrice);
		}
	}
	
	public synchronized void pingPlayerReady() {
		//isPinging = true;
		System.out.println("[SERVER] Pinging players ready...");
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
		//isPinging = false;
	}
	
	public synchronized void removePlayer(Player player) {
		this.players.remove(player);
	}
	
	public boolean isAllPlayersReady() {
		for (Player player : players) {
			if (player.ready == false) 
				return false;
		}
		return true;
	}
	
	public void setAllPlayersUnready() {
		for (Player player : players) {
			player.ready = false;
		}
	}
	
	public void finishRound() {
		getMap().finishRound();
		
		handleBids();
		
		setAllPlayersUnready();
		System.out.println("[SERVER] Finishing round...");
		incrementRound();
		
		Server.getInstance().sendMapUpdate();
		Server.getInstance().sendBroadcastMessage(new MessagePlayerReadyChange(players)); //is this needed?
		
		checkGameEnd();
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
					resourceRegion.winningBid = highestBid;
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
	
	public synchronized void addContract(ContractRequest request) {
		CityRegion cityRegion = (CityRegion) getMap().getRegion(request.coords);
		Contract newContract = new Contract(request.player, request.coords, request.awareness, 
				request.popularity, 0, 0.0, request.maxAmountEnergyNeeded, request.amountMoneyPerCustomer);
		cityRegion.addContract(newContract);
	}
	
	/*public synchronized void confirmContract(ContractRequestAnswer confirmation) {
		CityRegion cityRegion = (CityRegion) getMap().getRegion(confirmation.coords);
		cityRegion.setFreeCustomers(cityRegion.getFreeCustomers() - confirmation.contract.amountCustomer);
	}*/
	
	public synchronized void cancelContract(Contract cancelledContract) {
		CityRegion cityRegion = (CityRegion) getMap().getRegion(cancelledContract.coords);
		cityRegion.removeContract(cancelledContract);
	}
	
	public synchronized void finishBuilding(Coords coords, ResourceRegionStatus status)
	{
		ResourceRegion region = (ResourceRegion)getMap().getRegion(coords);
		region.setResourceRegionStatus(status);
	}

	public void checkGameEnd() {
		boolean gameEnd = false;
		
		int amountAllCustomers = 0;
		for (CityRegion cityRegion : getMap().getCityRegions()) {
			amountAllCustomers += cityRegion.getPopulation();
		}
		
		//Cycle through all cities to see if one player has more than x% of market share
		Player winningPlayer = null;
		for (Player player : players) {
			double marketShare = 0.0;
			
			for (CityRegion cityRegion : getMap().getCityRegions()) {
				Contract contract;
				if ( (contract = cityRegion.getPlayerContract(player)) != null) {
					marketShare += contract.amountCustomer / amountAllCustomers;
				}
			}
			
			if (marketShare >= Constants.GAME_END_MIN_MARKETSHARE && getRound() >= Constants.GAME_END_MIN_ROUNDS) {
				gameEnd = true;
				winningPlayer = player;
			}
			
		}
		if (players.size() == 1 && getRound() >= Constants.GAME_END_MIN_ROUNDS) {
			gameEnd = true;
			winningPlayer = players.get(0);
		}
		
		if (gameEnd == true) {
			Server.getInstance().sendGameEndMessage(winningPlayer);
		}
	}
	
}
