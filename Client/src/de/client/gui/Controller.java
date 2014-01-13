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
import de.shared.map.region.Coords;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceType;
import de.shared.map.relation.Contract;
import de.shared.map.relation.ContractRequestAnswer;


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
	
	public static Controller getInstance() {
		if (instance == null)
			instance = new Controller();
		
		return instance;
	}
	
	
	public void initGameLobby(ArrayList<Player> newPlayers) {
		frame = new Frame(newPlayers);
		frame.init();
	}
	
	public void updateGameLobby(ArrayList<Player> newPlayers) {
		if (frame != null)	{
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
	
	public void sendStartRegionBidding(Region region) {
		client.getClientGame().startResourceRegionBidding(region.coords);
	}
	
	public void sendRegionBid(Region region, double amount) {
		client.getClientGame().bidForResourceRegion(region.coords, amount);
	}
	
	public void initGame(Map map) {
		frame.initGame(map);
	}
	
	public void handleMapPanelClick() {
		frame.getPanelMain(null).getPanelDetails().setContentEmpty();
		frame.getPanelMain(null).getPanelDetails().refresh();
	}
	
	public void handleMapTileClick(HexagonButton hexButton) {
		frame.getPanelMain(null).getPanelDetails().setRegionContent(hexButton);
	}
	
	public Player getOwnPlayer() {
		return client.getClientGame().ownPlayer;
	}
	
	public Company getCompany() {
		return client.getClientGame().getCompany();
	}

	public void nextRound(Map map) {
		client.getClientGame().nextRound();
		
		frame.getPanelMain(null).getPanelMap().setMap(map);
		frame.getPanelMain(null).getPanelMap().init();
		
		frame.getPanelMain(null).getPanelDetails().setContentEmpty();
		frame.getPanelMain(null).getPanelDetails().refresh();
		
		frame.getPanelMenu().getButtonReady().setEnabled(true);
	}
	
	public void updatePanelDetails(HexagonButton hexButton) {
		hexButton.panel = null;
		frame.getPanelMain(null).getPanelDetails().setRegionContent(hexButton);
	}
	
	public void buildMine(ResourceRelation relation, ResourceType resourceType) {
		getCompany().buyMine(relation, resourceType);
	}
	
	public void buildPowerStation(ResourceRelation relation, ResourceType resourceType) {
		getCompany().buyPowerStation(relation, resourceType);
	}
	
	
	public static void main(String[] args) {
		//For testing
		Controller.getInstance();
	}

	public ClientGame getClientGame() {
		return client.getClientGame();
	}

	public void showContractRequestAnswer(ContractRequestAnswer answer) {
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
	}
	
	public void sendCancelContract(Coords coords, Contract contract, HexagonButton hexButton) {
		getClientGame().cancelContract(coords, contract);
		updatePanelDetails(hexButton);
	}

	public Frame getFrame() {
		return frame;
	}
}
