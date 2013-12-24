package de.server;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import de.shared.game.GamePhase;
import de.shared.game.Player;
import de.shared.message.Message;
import de.shared.message.server.MessageMapUpdate;

public class Server extends Thread {
	
	private static Server server;
	
	//private int amountPlayer = 0;
	//private int readyPlayer = 0;
	
	private ArrayList<Connection> connections;
	
	private ServerSocket serverSocket;

	private ServerGame serverGame;
	
	private Server()	{
		this.connections = new ArrayList<Connection>();
	}
	
	public static Server getInstance() {
		if (server == null) {
			server = new Server();
		}
		return server;
	}
	
	@Override
	public void run() {
		init();
	}
	
	public void init() {
		try {
			serverSocket = new ServerSocket(8989, 10, InetAddress.getLoopbackAddress()); //InetAddress.getLocalHost()
			
			this.serverGame = new ServerGame();
			
			System.out.println("[SERVER] SERVER IS LISTENING on " + serverSocket.getLocalSocketAddress());
			listenForPlayers();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized ServerGame getServerGame() {
		return serverGame;
	}
	
	public int getNumberConnections() {
		return connections.size();
	}
	
	public GamePhase getGamePhase() {
		return serverGame.gamePhase;
	}
	
	public void listenForPlayers() {
		try {
			while (getGamePhase() == GamePhase.PLAYERS_JOINING) {
				System.out.println("[SERVER] Listening for clients..");
				Socket clientSocket = serverSocket.accept();
				System.out.println("[SERVER] New client connected.");
				Connection client = new Connection(clientSocket);				
				connections.add(client);
				client.start();
			}
		}
		catch (Exception e) {
			//e.printStackTrace();
			System.out.println("[SERVER] Server socket closed: not accepting any new clients.");
		}
	}

	public synchronized void startGame() {
		System.out.println("[SERVER] STARTING GAME");
		//send clients the map
		try {
			if (serverSocket != null)
				serverSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sendMapUpdate();
		//listenDuringGame();
	}
	
	public synchronized void sendMapUpdate() {
		System.out.println("[SERVER] Broadcasting a map...");
		
		sendBroadcastMessage(new MessageMapUpdate(serverGame.getMap()));
	}
	
	public void sendBroadcastMessage(Message message) {
		for (Connection connection : connections) {
			connection.sendMessage(message);
		}
	}
	
	public void sendPlayerMessage(Player player, Message message) {
		for (Connection connection : connections) {
			if (connection.getPlayer().equals(player)) {
				connection.sendMessage(message);
			}
		}
	}
	
	/*public void listenDuringGame() {
		
		System.out.println("Game has started!");
		//Send
		
		while (getGamePhase() == GamePhase.GAME_STARTED) {
		
		}
		finishGame();
	}*/
	
	public void finishGame() {
		
	}
	
	public synchronized void addPlayer(Player player) {
		System.out.println("[SERVER] Player " + player.playerName + " has joined the lobby.");
		this.serverGame.addPlayer(player);
		
		//for (Connection connection : connections) {
			
		//}
	}
	
	public synchronized void pingPlayerReady() {
		serverGame.pingPlayerReady();
	}

	public void removeConnection(Connection connection) {
		this.connections.remove(connection);
		System.out.println("[SERVER] A client has disconnected.");
	}
	
	
	/*public void newPlayer (String name)
	{
		System.out.println("Player "+name+" ist dem Spiel beigetreten");
		amountPlayer++;
		System.out.println(amountPlayer+" Spieler sind jetzt im Spiel");
		/*for (int i = 0; i < connections.length; i++) {
			if (connections[i]!=null) {
				connections[i].resetReady(amountPlayer);				
			}			
		}
		for (Connection connection : connections) {
			connection.playerJoin(amountPlayer);
		}
		
	}*/

	
	
}
