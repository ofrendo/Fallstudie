package de.client;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import de.client.gui.Controller;
import de.shared.game.GamePhase;
import de.shared.game.Player;
import de.shared.map.Map;
import de.shared.message.Message;
import de.shared.message.MessageTypeToClient;
import de.shared.message.client.MessageInit;
import de.shared.message.client.MessageReady;

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
	
	public void setCompanyName(String companyName) {
		clientGame.getPlayer().companyName = companyName;
	}
	
	@Override
	public void run() {
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
		return this.clientGame;
	}
	
	public synchronized void sendMessage(Message message) {
		try {
			out.writeObject(message);
			out.flush();
			out.reset();
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
		sendMessage(new MessageInit(clientGame.ownPlayer));
	}
	
	public void sendReadyMessage(boolean ready) {
		if (getClientGame().gamePhase == GamePhase.GAME_STARTED) {
			clientGame.finishRound();
		}
		sendMessage(new MessageReady(ready));
	}
	
	public void setReady(boolean ready) {
		clientGame.ownPlayer.ready = ready;
	}

	@SuppressWarnings("unchecked")
	public void handleMessage() {
		boolean continueListening = true;
		Message message = null;
		try	{
			System.out.println("[CLIENT " + clientGame.ownPlayer.playerName + "] Listening for message from server...");
			message = (Message) in.readObject();
			MessageTypeToClient type = (MessageTypeToClient) message.getType();
			System.out.println("[CLIENT " + clientGame.ownPlayer.playerName + "] Recieved " + type + " message from server.");
			
			switch (type) {
			case RESET:
				int amountPlayer = (int) message.getValue();
				System.out.println("[CLIENT " + clientGame.ownPlayer.playerName + "] A new player has joined the lobby. " + amountPlayer+" Spieler sind nun im Spiel.");
				break;
			case INIT_REJECT:
				Controller.getInstance().triggerInitRejected();
				continueListening = false;
				break;
			case INIT_CONFIRM:
				ArrayList<Player> currentPlayers = (ArrayList<Player>) message.getValue();
				Controller.getInstance().triggerInitConfirmed(currentPlayers);
				break;
			case PLAYER_READY_CHANGE: 
				ArrayList<Player> players = (ArrayList<Player>) message.getValue();
				clientGame.setPlayers(players);
				
				if (clientGame.gamePhase == GamePhase.PLAYERS_JOINING) {
					Controller.getInstance().updateGameLobby(players);
				}
				else if (clientGame.gamePhase == GamePhase.GAME_STARTED) {
					
				}
				break;
			case MAP_UPDATE:
				Map newMap = (Map) message.getValue();
				clientGame.ownPlayer.ready = false;
				clientGame.setMap(newMap);
				if (clientGame.gamePhase == GamePhase.PLAYERS_JOINING) {
					clientGame.nextGamePhase();
					Controller.getInstance().initGame(newMap);
				}
				else if (clientGame.gamePhase == GamePhase.GAME_STARTED) {
					clientGame.nextRound();
					Controller.getInstance().nextRound(newMap);
				}
				break;
			/*case CONTRACT_REQUEST_ANSWER:
				answer = (ContractRequestAnswer) message.getValue();
				//SHOW IN GUI HERE
				Controller.getInstance().showContractRequestAnswer(answer);
				break;*/
			default:
				break;
			}
		} catch (Exception e) {
			System.out.println("[CLIENT " + clientGame.ownPlayer.playerName + "] Error recieving message from server. Disconnected.");
			e.printStackTrace();
			System.exit(0);
		}
		if (continueListening == true)
			handleMessage();
	}
	
	//TEST METHOD
	/*private ContractRequestAnswer answer;
	public ContractRequestAnswer getLatestContractAnswer() {
		return answer;
	}*/
}
