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
import de.shared.message.client.MessageChat;
import de.shared.message.client.MessageInit;
import de.shared.message.client.MessageReady;

/**
 * This class is the main communication point between the Client and the Server.
 */
public class Client extends Thread {

	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	
	private String serverAddress;
	
	private ClientGame clientGame;
	
	/**
	 * Client constructor.
	 * @param ipAddress The ipAddress of the server you want to connect to
	 * @param playerName Name of the player
	 */
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
	
	/**
	 * Tries to connect to the server.
	 */
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
	
	/**
	 * Sends a message to the server.
	 * @param message The Message that should be sent to the server
	 */
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
	
	/**
	 * This should be the first message to be sent - it tries to connect to the server and initializes the Player.
	 */
	public void sendInitMessage() {
		sendMessage(new MessageInit(clientGame.ownPlayer));
	}
	
	/**
	 * Sends the server the message that you are ready for the next round.
	 * @param ready true means ready, false means not ready
	 */
	public void sendReadyMessage(boolean ready) {
		if (getClientGame().gamePhase == GamePhase.GAME_STARTED) {
			clientGame.finishRound();
		}
		sendMessage(new MessageReady(ready));
	}
	
	public void setReady(boolean ready) {
		clientGame.ownPlayer.ready = ready;
	}
	
	/**
	 * Sends a broadcast chat message to the other players.
	 * @param message Message string to be sent
	 */
	public void sendChatMessage(String message) {
		sendMessage(new MessageChat(clientGame.getPlayer().playerName + ": " + message));
	}
	
	/**
	 * The main listening function. This will block, so it is called in run().
	 */
	@SuppressWarnings("unchecked")
	private void handleMessage() {
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
				if (!unitTesting) Controller.getInstance().triggerInitRejected();
				continueListening = false;
				break;
			case INIT_CONFIRM:
				ArrayList<Player> currentPlayers = (ArrayList<Player>) message.getValue();
				if (!unitTesting) Controller.getInstance().triggerInitConfirmed(currentPlayers);
				break;
			case PLAYER_READY_CHANGE: 
				ArrayList<Player> players = (ArrayList<Player>) message.getValue();
				clientGame.setPlayers(players);
				
				if (clientGame.gamePhase == GamePhase.PLAYERS_JOINING) {
					if (!unitTesting) Controller.getInstance().updateGameLobby(players);
				}
				else if (clientGame.gamePhase == GamePhase.GAME_STARTED) {
					if (!unitTesting) Controller.getInstance().getFrame().getPanelGlobalLeft().updatePlayerList(players);
				}
				break;
			case MAP_UPDATE:
				Map newMap = (Map) message.getValue();
				clientGame.ownPlayer.ready = false;
				clientGame.setMap(newMap);
				if (clientGame.gamePhase == GamePhase.PLAYERS_JOINING) {
					clientGame.nextGamePhase();
					if (!unitTesting) Controller.getInstance().initGame(newMap);
				}
				else if (clientGame.gamePhase == GamePhase.GAME_STARTED) {
					clientGame.nextRound();
					if (!unitTesting) Controller.getInstance().nextRound(newMap);
				}
				break;
			case GAME_END:
				boolean isWin = (boolean) message.getValue();
				clientGame.nextGamePhase();
				endGame(isWin);
				disconnect();
				continueListening = false;
				break;
			/*case CONTRACT_REQUEST_ANSWER:
				answer = (ContractRequestAnswer) message.getValue();
				//SHOW IN GUI HERE
				Controller.getInstance().showContractRequestAnswer(answer);
				break;*/
			case CHAT_BROADCAST:
				String stringMessage = (String) message.getValue();
				if (!unitTesting) Controller.getInstance().addChatMessage(stringMessage);
				break;
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
	
	private void disconnect() {
		try {
			if (in != null) 
				in.close();
			if (out != null)
				out.close();
			if (socket != null)
				socket.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Shows the user the end game panel and disconnects from the server.
	 * @param isWin
	 */
	public void endGame(boolean isWin) {
		String value = (isWin) ? "won" : "lost";
		String message = "You have " + value + " the game.";
		if (!unitTesting) Controller.getInstance().getFrame().setPanelGameEnd(message);
		disconnect();
	}
	
	//TEST METHOD
	/*private ContractRequestAnswer answer;
	public ContractRequestAnswer getLatestContractAnswer() {
		return answer;
	}*/
	private boolean unitTesting = false;
	/**
	 * Call this function to start unit test mode - the Controller will not be notified and no GUI elements will appear.
	 */
	public void TEST_setUnitTestMode() {
		unitTesting = true;
	}

}
