package de.client.gui;

import java.lang.Thread.State;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import de.client.Client;
import de.client.ClientGame;
import de.client.company.Company;
import de.client.company.ResourceRelation;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.region.CityRegion;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceType;
import de.shared.map.relation.Contract;

/**
 * This class controls access from the GUI to the model. This class is a singleton.
 */
public class Controller {

	private static Controller instance;
	
	private Client client;
	private Frame frame;
	private FrameConnect frameConnect;
	
	public HexagonButton lastHexButton;
	
	private Controller() {
		frameConnect = new FrameConnect();
		frameConnect.init();
		client = new Client(frameConnect.getIPAddress(), frameConnect.getPlayerName());
	}
	
	/**
	 * Gets the singleton instance.
	 * @return The instance
	 */
	public static Controller getInstance() {
		if (instance == null)
			instance = new Controller();
		
		return instance;
	}
	
	
	public void initGameLobby(ArrayList<Player> newPlayers) {
		frame = new Frame(newPlayers);
		frame.init(newPlayers);
	}
	
	public void updateGameLobby(ArrayList<Player> newPlayers) {
		if (frame != null)	{
			frame.setInitPlayers(newPlayers);
			frame.getPanelLobby().updateReadyStatus(newPlayers);
			frame.refresh();
		}
	}
	
	public void connect() {
		//Connect to server
		//Test if state has already started
		if (client.getState() != State.RUNNABLE) {
			client = new Client(frameConnect.getIPAddress(), frameConnect.getPlayerName());
		}
		client.setCompanyName(frameConnect.getCompanyName());
		client.connectToServer();
		client.sendInitMessage();
		client.start();
	}
	
	public void triggerInitRejected() {
		frameConnect.showInitRejected();
	}
	
	public void triggerInitConfirmed(ArrayList<Player> players) {
		//Open lobby gui
		initGameLobby(players);
		
		//Close connecting GUI
		frameConnect.setVisible(false); 
		frameConnect.dispose();
	}
	
	
	public void sendReady(boolean ready) {
		client.sendReadyMessage(ready);
	}
	
	public void checkAndSendReady() {
		if (!client.getClientGame().isEnoughResourcesAvailable()) {
			//Not enough resources
			String message = "Nicht genügend Rohstoffe vorhanden.";
			JOptionPane.showMessageDialog(frame, message);
		}
		else if (client.getClientGame().getSuperflousEnergy() < 0) {
			//Not enough energy produced
			double amountEnergyNeeded = client.getClientGame().getSuperflousEnergy();
			double sumPrice = amountEnergyNeeded * getClientGame().getMap().getEnergyExchange().getCurrentEnergyPrice();
			
			String message = "<html><table>"
					+ "<tr>"
					+ "<td>Nicht genug Energieproduktion!</td>"
					+ "<td></td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>" + Strings.fD(amountEnergyNeeded) + " kWh Energie dazukaufen für</td>"
					+ "<td>" + Strings.fD(sumPrice) + " €</td>"
					+ "</tr>"
					+ "<tr>"
					+ "<td>ODER alle folgenden Verträge kündigen:</td>"
					+ "<td></td>"
					+ "</tr>";

			//All cities with not enough energy supplied
			ArrayList<CityRegion> cityRegions = getCompany().getCityRegionsWithNotEnoughEnergy();
			
			for (CityRegion cityRegion : cityRegions) {
				message += "<tr>"
						+ "<td>" + cityRegion.getCityName() + "</td><td></td>"
						+ "</tr>";
			}
			
			message += "</table></html>";

			Object[] options = {
					"Energie dazukaufen",
			        "Vertrag kündigen",
			        "Abbrechen"
			};
			
			int n = JOptionPane.showOptionDialog(
					frame,
					message,
					"Nicht genügend Energie",
					JOptionPane.YES_NO_CANCEL_OPTION,
					JOptionPane.QUESTION_MESSAGE,
					null,     //do not use a custom Icon
					options,  //the titles of buttons
					options[0] //default button title
			); 
			
			if (n == JOptionPane.YES_OPTION) { //Buy energy
				
			}
			else if (n == JOptionPane.NO_OPTION) { //Cancel contracts
				
			}
		}
		
		
		else {
			//No problems
			frame.getPanelMenu().getButtonReady().setEnabled(false);
			sendReady(true);
		}
	}
	
	public void sendStartRegionBidding(Region region) {
		client.getClientGame().startResourceRegionBidding(region.coords);
	}
	
	public void sendRegionBid(Region region, double amount) {
		client.getClientGame().bidForResourceRegion(region.coords, amount);
		resetPanelMenuInformation();
	}
	
	public void initGame(Map map) {
		frame.initGame(map);
	}
	
	public void handleMapPanelClick() {
		this.lastHexButton = null;
		frame.getPanelMain(null).getPanelMap().refresh();
		frame.getPanelMain(null).getPanelDetails().setContentEmpty();
		frame.getPanelMain(null).getPanelDetails().refresh();
	}
	
	public void handleMapTileClick(HexagonButton hexButton) {
		this.lastHexButton = hexButton;
		frame.getPanelMain(null).getPanelMap().refresh();
		frame.getPanelMain(null).getPanelDetails().setRegionContent(hexButton);
	}
	
	public Player getOwnPlayer() {
		return client.getClientGame().ownPlayer;
	}
	
	public Company getCompany() {
		return client.getClientGame().getCompany();
	}

	public void nextRound(Map map) {
		resetPanelMenuInformation();
		frame.resetActivePanel(map);
		getFrame().getPanelGlobalLeft().updatePanelRoundTitle(getClientGame().getRound());
	}
	
	public void updatePanelDetails(HexagonButton hexButton) {
		hexButton.panel = null;
		frame.getPanelMain(null).getPanelDetails().setRegionContent(hexButton);
	}
	
	public void buildMine(ResourceRelation relation, ResourceType resourceType) {
		getCompany().buyMine(relation, resourceType);
		resetPanelMenuInformation();
	}
	
	public void buildPowerStation(ResourceRelation relation, ResourceType resourceType) {
		getCompany().buyPowerStation(relation, resourceType);
		resetPanelMenuInformation();
	}
	
	public void resetPanelMenuInformation() {
		getFrame().getPanelMenu().getPanelMenuInformation().reset();
	}
	
	public static void main(String[] args) {
		//For testing
		Controller.getInstance();
	}

	public Client getClient() {
		return client;
	}
	
	public ClientGame getClientGame() {
		return client.getClientGame();
	}

	/*public void showContractRequestAnswer(ContractRequestAnswer answer) {
		Object[] options = {
				"Vertrag annehmen",
		        "Vertrag ablehnen"
		};
		Contract c = answer.contract;
		String content = "<html><table>"
				+ "<tr>"
				+ "<td>Anzahl Kunden:</td>"
				+ "<td>" + c.amountCustomer + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Preis pro Kunde:</td>"
				+ "<td>" + Strings.fD(c.amountMoneyPerCustomer) + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Benötigte Energie:</td>"
				+ "<td>" + Strings.fD(c.amountEnergyNeeded) + " Mw/h</td>"
				+ "</tr>"
				+ "</table></html>";
		
		int n = JOptionPane.showOptionDialog(
				frame,
				content,
				"Vertragsangebot",
				JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE,
				null,     //do not use a custom Icon
				options,  //the titles of buttons
				options[1] //default button title
		); 
		
		if (n == JOptionPane.YES_OPTION) {
			client.getClientGame().acceptContract(answer);
			updatePanelDetails(lastHexButton);
		}
	}*/
	
	public void addChatMessage(String stringMessage) {
		frame.getPanelGlobalLeft().addChatMessage(stringMessage);
	}
	
	public void sendCancelContract(Contract contract, HexagonButton hexButton) {
		getClientGame().cancelContract(contract);
		//updatePanelDetails(hexButton);
	}
	
	public String getHtmlAccountInformation() {
		double currentIncome = getCompany().getCurrentIncome();
		double currentExpenditures = getCompany().getCurrentExpenditures();
		String differenceString = (currentIncome > currentExpenditures) ? "Gewinn: " : "Verlust: ";
		
		String htmlAccountInformation = "<html><table>"
				+ "<tr>"
				+ "<td>" + getOwnPlayer().companyName + "</td><td></td>"
				+ "</tr>"
				+ "<tr></tr>"
				+ "<tr>"
				+ "<td>Konto: </td>"
				+ "<td align='right'>" + Strings.fD(getCompany().getMoney()) + "€</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Einnahmen: </td>"
				+ "<td align='right'>" + Strings.fD(currentIncome) + "€</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Ausgaben: </td>"
				+ "<td align='right'>" + Strings.fD(currentExpenditures) + "€</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>" + differenceString + "</td>"
				+ "<td align='right'>" + Strings.fD(Math.abs(currentIncome-currentExpenditures)) + "€</td>"
				+ "</tr>"
				+ "<tr><td>Schulden: </td><td align='right'>" + Strings.fD(getCompany().getFinances().getDebtCapital()) + "€</td></tr>"
				+ "</table></html>";
		
		return htmlAccountInformation;
	}
	
	public String getHtmlResourceInformation() {
		String htmlResourceInformation = "<html><table>"
				+ "<tr>" +  getHtmlResourceRow(ResourceType.COAL) + "</tr>"
				+ "<tr>" +  getHtmlResourceRow(ResourceType.GAS) + "</tr>"
				+ "<tr>" +  getHtmlResourceRow(ResourceType.URANIUM) + "</tr>"
				+ "</table></html>"; 
		return htmlResourceInformation;
	} 
	
	public String getHtmlResourceRow(ResourceType resourceType) {
		String resourceAmount = Strings.fD(getCompany().getWarehouse()
									   .getWare(resourceType).getAmount());
		
		double resourceProduction = getCompany().getResourceProduction(resourceType, false);
		double resourceConsumption = getCompany().getResourceConsumption(resourceType);
		
		String rAPerRound = Strings.fD(resourceProduction - resourceConsumption);
		String symbol = (resourceProduction >= 0) ? "+" : "";
		String unit = Strings.getResourceUnit(resourceType);
		return "<td>" + Strings.getResourceString(resourceType) + ": </td>"
				+ "<td align='right'>" + resourceAmount + unit + "</td>"
				+ "<td align='right'> " + symbol + " " + rAPerRound + unit + " pro Quartal</td>";
	}
	
	public String getHtmlEnergyInformation() {
		return "<html><table>"
				+ "<tr>"
				+ "<td>Stromproduktion:</td>"
				+ "<td align='right'>" + Strings.fD(getCompany().getEnergyProductionSum()) + " " 
						 + Strings.ENERGY_UNIT + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Stromverbrauch:</td>"
				+ "<td align='right'>" + Strings.fD(getCompany().getEnergyNeededSum()) + " "  
						 + Strings.ENERGY_UNIT + "</td>"
				+ "</tr>"
				+ "<tr>"
				+ "<td>Strombilanz:</td>"
				+ "<td align='right'>" + Strings.fD(getCompany().getSuperflousEnergy()) + " "
						 + Strings.ENERGY_UNIT + "</td>"
				+ "</tr>"
				+ "</table></html>";
	}
	
	public void showMessageDialog(String message) {
		JOptionPane.showMessageDialog(null, message);
	}
	
	public Frame getFrame() {
		return frame;
	}

	//TEST METHODS for unit testing, to remove annoying popups
	public void setUnitTestMode() {
		if (frameConnect != null)
			frameConnect.setVisible(false);
		if (frame != null)
			frame.setVisible(false);
	}

}
