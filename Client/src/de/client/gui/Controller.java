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

	private ArrayList<Player> allPlayers;
	
	public void updateGameLobby(ArrayList<Player> newPlayers) {
		if (frame != null)	{
			frame.getPanelLobby().updateReadyStatus(newPlayers);
			frame.refresh();
		}
		else {
			allPlayers = newPlayers;
		}
	}
	
	public void connect() {
		//Connect to server
		client = new Client(frameConnect.getIPAddress(), frameConnect.getPlayerName());
		client.setCompanyName(frameConnect.getCompanyName());
		client.connectToServer();
		client.sendInitMessage();
		client.start();
		
		if (allPlayers == null) {
			allPlayers = new ArrayList<Player>();
			allPlayers.add(client.getClientGame().ownPlayer);
		}
		
		//Open lobby gui
		initGameLobby(allPlayers);
		
		//Close connecting GUI
		frameConnect.setVisible(false); 
		frameConnect.dispose();
	}

	public void sendReady() {
		client.sendReadyMessage();
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
