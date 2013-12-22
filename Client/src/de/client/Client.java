package de.client;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import de.client.message.MessageInit;
import de.client.message.MessageReady;
import de.shared.game.GamePhase;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.map.relation.*;
import de.shared.message.Message;
import de.shared.message.MessageTypeToClient;

public class Client extends Thread {
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private String serverAddress;
	
	private ClientGame clientGame;
	
	public Client(String ipAddress, String playerName) {
		this.serverAddress = ipAddress;
		this.clientGame = new ClientGame(this, new Player(playerName));
	}
	
	@Override
	public void run() {
		connectToServer();
		sendInitMessage();
		handleMessage();
	}
	
	public void connectToServer() {
		try {
			System.out.println("[CLIENT] Connecting to server...");
			socket = new Socket (serverAddress, 8989 );
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			System.out.println("[CLIENT] Connected to server.");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public ClientGame getClientGame() {
		return clientGame;
	}
	
	public void sendMessage(Message message) {
		try {
			out.writeObject(message);
			out.flush();
			/*if (socket == null) {
				throw new Exception("Keine Serververbindung aufgebaut");
			}
			if (out == null) {
				out = new ObjectOutputStream(socket.getOutputStream());			
			}*/
		} catch (Exception e) {
			System.out.println("Error sending a message");
			System.out.println("Type: " + message.getType());
			e.printStackTrace();
		}
	}
	
	public void sendInitMessage() {
		sendMessage(new MessageInit(clientGame.ownPlayer.playerName));
	}
	
	public void sendReadyMessage() {
		sendMessage(new MessageReady());
		//handleMessage();
	}
	
	public void setReady(boolean ready) {
		clientGame.ownPlayer.ready = ready;
	}

	@SuppressWarnings("unchecked")
	public void handleMessage() {
		Message message = null;
		try	{
			System.out.println("[CLIENT] Listening for message from server...");
			message = (Message) in.readObject();
			MessageTypeToClient type = (MessageTypeToClient) message.getType();
			System.out.println("[CLIENT] Recieved " + type + " message from server.");
			
			switch (type) {
			case RESET:
				int amountPlayer = (int) message.getValue();
				System.out.println("[CLIENT] A new player has joined the lobby. " + amountPlayer+" Spieler sind nun im Spiel.");
				break;
			case PLAYER_READY_CHANGE: 
				ArrayList<Player> players = (ArrayList<Player>) message.getValue();
				clientGame.setPlayers(players);
				break;
			case MAP_UPDATE:
				Map newMap = (Map) message.getValue();
				if (clientGame.gamePhase == GamePhase.PLAYERS_JOINING) {
					clientGame.nextGamePhase();
				}
				else if (clientGame.gamePhase == GamePhase.GAME_STARTED) {
					clientGame.incrementRound();
				}
				clientGame.ownPlayer.ready = false;
				clientGame.setMap(newMap);
				break;
			case CONTRACT_REQUEST_ANSWER:
				answer = (ContractRequestAnswer) message.getValue();
				//SHOW IN GUI HERE
				
				
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		handleMessage();
	}
	
	//TEST METHOD
	private ContractRequestAnswer answer;
	public ContractRequestAnswer getLatestContractAnswer() {
		return answer;
	}
}
