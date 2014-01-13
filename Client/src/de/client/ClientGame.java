package de.client;

import java.util.ArrayList;

import de.client.company.Company;
import de.client.optimization.Optimizer;
import de.shared.game.Game;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Coords;
import de.shared.map.region.FinishedBuilding;
import de.shared.map.relation.CityRelation;
import de.shared.map.relation.Contract;
import de.shared.map.relation.ContractRequest;
import de.shared.map.relation.ContractRequestAnswer;
import de.shared.message.client.MessageBuildingFinished;
import de.shared.message.client.MessageContractCancel;
import de.shared.message.client.MessageContractConfirm;
import de.shared.message.client.MessageRequestContract;
import de.shared.message.client.MessageResourceRegionBid;
import de.shared.message.client.MessageStartResourceRegionBidding;
import de.shared.message.client.MessageTradeEnergy;

public class ClientGame extends Game {

	public Player ownPlayer;
	private Client client;
	private Company company;
	
	public ClientGame(Client client, Player player) {
		super();
		this.ownPlayer = player;
		this.client = client;
		
		this.company = new Company(player.companyName, client);
	}
	
	public Company getCompany() {
		return company;
	}
	
	public void setMap(Map newMap) {
		if (this.map == null) {
			company.initRelations(newMap.getRegions());
		}
		this.map = newMap;
	}
	
	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
	}
	
	public Player getPlayer() {
		return ownPlayer;
	}
	
	public void startResourceRegionBidding(Coords coords) {
		client.sendMessage(new MessageStartResourceRegionBidding(coords));
	}
	
	public void bidForResourceRegion(Coords coords, double amount) {
		client.sendMessage(new MessageResourceRegionBid(coords, ownPlayer, amount));
	}
	
	public void requestContract(CityRegion cityRegion, int maxCustomers, double amountMoneyPerCustomer) {
		CityRelation relationToCity = (CityRelation) company.getRegionRelation(cityRegion.coords);
		double popularity = relationToCity.popularity;
		double awareness = relationToCity.awareness;
		ContractRequest request = new ContractRequest(cityRegion.coords, maxCustomers, amountMoneyPerCustomer, popularity, awareness);
		client.sendMessage(new MessageRequestContract(request));
	}
	
	public void acceptContract(ContractRequestAnswer answer) {
		CityRelation cityRelation = (CityRelation) getCompany().getRegionRelation(answer.coords);
		cityRelation.setContract(answer.contract);

		confirmContract(answer);
		
		Optimizer.optimizePowerStations(company.getPowerStations(), company.getCityRelationsWithContract());
	}
	
	public void confirmContract(ContractRequestAnswer answer) {
		client.sendMessage(new MessageContractConfirm(answer));
	}
	
	public void cancelContract(Coords coords, Contract contract) {
		ContractRequestAnswer cancellation = new ContractRequestAnswer(coords, contract);
		client.sendMessage(new MessageContractCancel(cancellation));
		
		CityRelation relation = (CityRelation) company.getRegionRelation(coords);
		relation.setContract(null);
	}
	public void sendFinishBuilding(FinishedBuilding finishedBuilding)
	{
		client.sendMessage(new MessageBuildingFinished(finishedBuilding));
	}
	
	/**
	 * 
	 * @param amountEnergy POSITIVE value means an offer is made (energy is sold). NEGATIVE value means energy was bought.
	 */
	public void sendTradeEnergy(double amountEnergy) {
		client.sendMessage(new MessageTradeEnergy(amountEnergy));
	}
	
	public void nextRound() {
		incrementRound();
		//company.nextRound();
	}

	public void finishRound() {
		company.finishRound();
		
	}
	
}
