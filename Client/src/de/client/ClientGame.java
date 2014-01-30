package de.client;

import java.util.ArrayList;

import de.client.company.Building;
import de.client.company.Company;
import de.client.company.PowerStation;
import de.client.company.ResourceRelation;
import de.shared.game.Constants;
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
	private ArrayList <EventMessage> events;
	
	public ClientGame(Client client, Player player) {
		super();
		this.ownPlayer = player;
		this.client = client;
		this.events = new ArrayList<EventMessage>();
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
			compareMap(newMap);
		}
		this.map = newMap;
	}
	
	public ArrayList<EventMessage> getEventMessages() {
		return events;
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
					events.add(new EventMessage("Eine Region steht zum Verkauf", resRegionNew.coords));
				}
				//compare owned status
				if (resRegionNew.resourceRegionStatus == ResourceRegionStatus.OWNED &&
						resRegionOld.resourceRegionStatus == ResourceRegionStatus.BUYABLE) {
					
					String message;
					if (resRegionNew.owner.equals(ownPlayer)) {
						message = "Du hast die Auktion einer Region gewonnen!";
					}
					else {
						ResourceRelation resourceRelation = (ResourceRelation) company.getRegionRelation(resRegionNew.coords);
						if (resourceRelation.bid > 0) {
							message = "Du hast die Auktion einer Region verloren!";
							//Give money back
							company.setMoney(company.getMoney() + resourceRelation.bid);
							resourceRelation.bid = 0;
						}
						else {
							message = "Ein anderer Spieler hat eine Region gekauft.";
						}
					}
					
					events.add(new EventMessage(message , resRegionNew.coords));
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
						events.add(new EventMessage("Wir haben eine Mine gebaut", resRegionNew.coords));
					}
					
					//get new Powerstation
					if (	//Powerstation is new Status and Owned is old Status
							((resRegionNew.resourceRegionStatus == ResourceRegionStatus.POWERSTATION && 	
							resRegionOld.resourceRegionStatus == ResourceRegionStatus.OWNED) ||
							//Mine_PowerStation is new , Mine is old
							(resRegionNew.resourceRegionStatus == ResourceRegionStatus.MINE_POWERSTATION && 
							resRegionOld.resourceRegionStatus == ResourceRegionStatus.MINE )))
					{
						events.add(new EventMessage("Wir haben ein Kraftwerk gebaut", resRegionNew.coords));
					}
					
					//get new Powerstation AND mine
					if ( 	resRegionNew.resourceRegionStatus == ResourceRegionStatus.MINE_POWERSTATION &&
							resRegionOld.resourceRegionStatus == ResourceRegionStatus.OWNED	) 
					{
						events.add(new EventMessage("Wir haben eine Mine gebaut", resRegionNew.coords));
						events.add(new EventMessage("Wir haben ein Kraftwerk gebaut", resRegionNew.coords));
					}
				}
			}
			
			if (region instanceof CityRegion) {
				//get new and old city region
				Contract contractOld=null;
				CityRegion cityRegionNew = (CityRegion) region;
				int index = regionsNew.indexOf(region);
				CityRegion cityRegionOld = (CityRegion) regionsOld.get(index);
				
				//get all contracts from each region
				ArrayList<Contract> contractsNew = cityRegionNew.getContracts();
				ArrayList<Contract> contractsOld = cityRegionOld.getContracts();
				for (Contract contractNew : contractsNew) {
					if (contractNew.getPlayer().equals(ownPlayer)) {
						
						//get the old contract
						for (Contract contract : contractsOld) {
							if(contract.getPlayer().equals(contractNew.getPlayer()))
							{
								contractOld = contract;
								break;
							}
						}
						
						//compare both contracts
						if (contractOld != null) {						
							if (contractNew.amountCustomer>contractOld.amountCustomer) {
								
								events.add(new EventMessage("Wir haben in "+cityRegionNew.getCityName()+" einige Kunden hinzugewonnen", cityRegionNew.coords));
							}
							else if (contractNew.amountCustomer<contractOld.amountCustomer) {
								events.add(new EventMessage("Wir haben in "+cityRegionNew.getCityName()+" einige Kunden verloren", cityRegionNew.coords));
							}
						}
						else
						{

							events.add(new EventMessage("Wir haben in "+cityRegionNew.getCityName()+" neue Kunden hinzugewonnen", cityRegionNew.coords));
						}
						
						contractOld = null;
							
					}
				}
				//check if there is an old contract , but not a new one
				boolean contractDeleted = true;
				for (Contract contract : contractsOld) {
					for (Contract contractNew : contractsNew) {
						if (contract.getPlayer().equals(contractNew.getPlayer())) {
							contractDeleted = false;
							break;
						}
					}
					
					if (contractDeleted) {
						events.add(new EventMessage("Wir haben in "+cityRegionNew.getCityName()+" alle Kunden verloren", contract.coords));
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
		((ResourceRelation) company.getRegionRelation(coords)).bid = amount;
		company.setMoney(company.getMoney() - amount);
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
		triggerEvents();
		company.optimizePowerStations();
	}

	public void triggerEvents() {
		double triggerModificator = 0.0; // Platzhalter für spätere prozentuelle Warscheinlichkeiten von Events
		double eventCosts = 0.0 ;
		
		ArrayList<Building> buildings = company.getBuildings();
		for (Building building : buildings) {
			if(building.isBuilt())
			{
				if(building instanceof PowerStation)
				{
					PowerStation ps = (PowerStation) building;
					if(ps.maintenanceRate < 1.0 && ps.maintenanceRate > 0.5)  // if maintenance rate is less than 100% but more than 50%
					{
						triggerModificator = (1.0 - ps.maintenanceRate ) / 10;    // between 0 % and 5 %
						if(Math.random()<=triggerModificator)
						{
							//Small Meintenece Problem
							eventCosts = ps.getResourceType().pPurchaseValue*Constants.MAINTENANCE_ACCIDENT_SMALL;
							events.add(new EventMessage("Eine notwendige Reparatur in einem Kraftwerk kostete dir "+eventCosts+" €.", ps.coords));
							//pay costs and add them to balance
							company.setMoney(company.getMoney()-eventCosts);  
							company.getFinances().getBalance().getProfitAndLoss().addOtherCosts(eventCosts);
						}
					}
				}
			}
		}
		
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
