package de.client.gui;

import java.util.ArrayList;

import de.client.Client;
import de.client.ClientGame;
import de.client.company.Company;
import de.client.company.ResourceRelation;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.region.Region;
import de.shared.map.region.ResourceType;


public class Controller {

	private static Controller instance;
	
	private Client client;
	private Frame frame;
	private FrameConnect frameConnect;
	
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
		
		frame.getPanelMain(null).getPanelDetails().getButtonReady().setEnabled(true);
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
}
