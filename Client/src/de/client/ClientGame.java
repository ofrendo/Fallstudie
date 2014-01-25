package de.client;

import java.util.ArrayList;

import de.client.company.Company;
import de.shared.game.Game;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Coords;
import de.shared.map.region.FinishedBuilding;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceRegion;
import de.shared.map.region.ResourceRegionStatus;
import de.shared.map.relation.CityRelation;
import de.shared.map.relation.Contract;
import de.shared.map.relation.ContractRequest;
import de.shared.message.client.MessageBuildingFinished;
import de.shared.message.client.MessageContractCancel;
import de.shared.message.client.MessageRequestContract;
import de.shared.message.client.MessageResourceRegionBid;
import de.shared.message.client.MessageStartResourceRegionBidding;
import de.shared.message.client.MessageTradeEnergy;

public class ClientGame extends Game {

	public Player ownPlayer;
	private Client client;
	private Company company;
	public ArrayList <EventMessage> events;
	
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
		else
		{
			if (events == null) {
				events = new ArrayList<>();
			}
			compareMap(newMap);
		}
		this.map = newMap;
	}
	
	private void compareMap(Map newMap) {
		//compares the old Map with the new map to create EventMessages, so that the Player knows what's going on in the game
		ArrayList<Region> regionsNew = newMap.getRegions();
		ArrayList<Region> regionsOld = map.getRegions();
		
		for (Region region : regionsNew) {
			
			//compare RessourceRegions
			if(region instanceof ResourceRegion)
			{
				ResourceRegion resRegionNew = (ResourceRegion) region;
				int index = regionsNew.indexOf(region);
				ResourceRegion resRegionOld = (ResourceRegion) regionsOld.get(index) ;
				
				//compare buyable status
				if (resRegionNew.resourceRegionStatus == ResourceRegionStatus.BUYABLE ) {
					//region steht zum verkauf
					events.add(new EventMessage("Region steht zum Verkauf", resRegionNew.coords));
					System.out.println("Region wird zum Verkauf angeboten");
				}
				//compare owned status
				if (resRegionNew.resourceRegionStatus == ResourceRegionStatus.OWNED &&
						resRegionOld.resourceRegionStatus == ResourceRegionStatus.BUYABLE) {
					events.add(new EventMessage("Eine Region wurde gekauft" , resRegionNew.coords));
					System.out.println("eine region wurde gekauft");
				}
				
				//compare map related to own buildings
				if (ownPlayer.equals(resRegionNew.owner)) {
					//get new Mine		
					if (   //Mine is new Status and Owned is old Status
							((resRegionNew.resourceRegionStatus == ResourceRegionStatus.MINE && 	
							resRegionOld.resourceRegionStatus == ResourceRegionStatus.OWNED) ||
							//Mine_PowerStation is new , Powerstation is old
							(resRegionNew.resourceRegionStatus == ResourceRegionStatus.MINE_POWERSTATION && 
							resRegionOld.resourceRegionStatus == ResourceRegionStatus.POWERSTATION )))
					{
						events.add(new EventMessage("Eine Mine wurde gebaut", resRegionNew.coords));
						System.out.println("Eine mine wurde gebaut");
					}
					
					//get new Powerstation
					if (	//Powerstation is new Status and Owned is old Status
							((resRegionNew.resourceRegionStatus == ResourceRegionStatus.POWERSTATION && 	
							resRegionOld.resourceRegionStatus == ResourceRegionStatus.OWNED) ||
							//Mine_PowerStation is new , Mine is old
							(resRegionNew.resourceRegionStatus == ResourceRegionStatus.MINE_POWERSTATION && 
							resRegionOld.resourceRegionStatus == ResourceRegionStatus.MINE )))
					{
						events.add(new EventMessage("Ein Kraftwerk wurde gebaut", resRegionNew.coords));
						System.out.println("Ein Kraftwerk wurde gebaut");
					}
					
				}
				
				
			
			

				
			}
			
			if (region instanceof CityRegion) {
				
				CityRegion cityRegionNew = (CityRegion) region;
				int index = regionsNew.indexOf(region);
				CityRegion cityRegionOld = (CityRegion) regionsOld.get(index);
				
				ArrayList<Contract> contractsNew = cityRegionNew.getContracts();
				for (Contract contractNew : contractsNew) {
					if (contractNew.getPlayer().equals(ownPlayer)) {
						int contractIndex = contractsNew.indexOf(contractNew);
						if (cityRegionOld.getContracts().size()>contractIndex) {
							
							Contract contractOld = cityRegionOld.getContracts().get(contractIndex);
							if (contractNew.amountCustomer>contractOld.amountCustomer) {
								events.add(new EventMessage("wir haben neue kunden hinzugewonnen", cityRegionNew.coords));
							}
							else if (contractNew.amountCustomer<contractOld.amountCustomer) {
								events.add(new EventMessage("wir haben einige kunden verloren", cityRegionNew.coords));
							}
						}
						
						
						
					}
				}
				
			}
			
		}
		
		
		
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
	
	public void requestContract(CityRegion cityRegion, double maxAmountEnergyNeeded, double amountMoneyPerCustomer) {
		CityRelation relationToCity = (CityRelation) company.getRegionRelation(cityRegion.coords);
		double popularity = relationToCity.popularity;
		double awareness = relationToCity.awareness;
		
		ContractRequest request = new ContractRequest(ownPlayer, cityRegion.coords, amountMoneyPerCustomer, popularity, awareness, maxAmountEnergyNeeded);
		client.sendMessage(new MessageRequestContract(request));
	}
	
	/*public void acceptContract(ContractRequestAnswer answer) {
		//CityRelation cityRelation = (CityRelation) getCompany().getRegionRelation(answer.coords);
		//cityRelation.setContract(answer.contract);

		confirmContract(answer);
		
		
	}
	
	public void confirmContract(ContractRequestAnswer answer) {
		client.sendMessage(new MessageContractConfirm(answer));
	}*/
	
	public void cancelContract(Contract contract) {
		client.sendMessage(new MessageContractCancel(contract));
		
		//CityRelation relation = (CityRelation) company.getRegionRelation(coords);
		//relation.setContract(null);
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
		company.optimizePowerStations();
	}

	public boolean isEnoughResourcesAvailable() {
		return company.isEnoughResourcesAvailable();
	}

	public double getSuperflousEnergy() {
		return company.getSuperflousEnergy();
	}
	
	public void finishRound() {
		events.clear();
		company.finishRound();
	}
	
}
