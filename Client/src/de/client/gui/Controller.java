package de.client.gui;

import java.util.ArrayList;

import de.client.Client;
import de.shared.game.Player;
import de.shared.map.Map;


public class Controller {

	private static Controller instance;
	
	private Client client;
	private Frame frame;
	private FrameConnect frameConnect;
	
	private Controller() {
		frameConnect = new FrameConnect();
		frameConnect.init();
	}
	
	public static Controller getInstance() {
		if (instance == null)
			instance = new Controller();
		
		return instance;
	}
	
	
	private void initGameLobby(ArrayList<Player> newPlayers) {
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
		client = new Client(frameConnect.getIPAddress(), frameConnect.getPlayerName());
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
	
	public void initGame(Map map) {
		frame.initGame(map);
	}
	
	public void handleMapPanelClick() {
		
	}
	
	public void handleMapTileClick(HexagonButton hexButton) {
		
	}
	
	public static void main(String[] args) {
		//For testing
		Controller.getInstance();
	}


}
