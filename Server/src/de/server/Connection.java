package de.server;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import de.shared.game.Player;
import de.shared.map.region.Coords;
import de.shared.map.region.ResourceRegionBid;
import de.shared.map.relation.Contract;
import de.shared.map.relation.ContractRequest;
import de.shared.map.relation.ContractRequestAnswer;
import de.shared.map.relation.MessageContractRequestAnswer;
import de.shared.message.Message;
import de.shared.message.MessageTypeToServer;
import de.shared.message.server.MessageInitConfirm;
import de.shared.message.server.MessageInitReject;

public class Connection extends Thread {

	private Socket clientSocket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public boolean reading = true;
	
	private Player player;
	
	public Connection(Socket clientSocket) {
		try	{
			this.clientSocket = clientSocket;
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			in = new ObjectInputStream(clientSocket.getInputStream());
			//BLOCKS IF in is done before out
		} catch (IOException e) {
			//System.out.println("Error listening to client.");
			//e.printStackTrace();
			//Means client has disconnected
			Server.getInstance().removeConnection(this);
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	
	@Override
	public void run () {
		try {
			handleMessage();			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			/*if (clientSocket != null) {
				try {
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}*/
		}
	}
	
	//Server threads listen during round
	//If every player is ready: send end of round information
	private void handleMessage()  {
		while (reading == true) { //Server.getInstance().getGamePhase() == GamePhase.PLAYERS_JOINING && 
			Message message = null;
			try {
				System.out.println("[SERVER] Listening for message from client...");
				message = (Message) in.readObject();
				MessageTypeToServer type = (MessageTypeToServer) message.getType();
				System.out.println("[SERVER] Recieved " + type + " message from client.");
				
				switch (type) {
				case INIT: 
					Player newPlayer = (Player) message.getValue();
					
					Message reply;
					if (!Server.getInstance().isNameExists(newPlayer.playerName, newPlayer.companyName)) {
						this.player = newPlayer;
						Server.getInstance().addPlayer(newPlayer);
						reply = new MessageInitConfirm( Server.getInstance().getServerGame().getPlayers() );
						
						
						while (Server.getInstance().getServerGame().isPinging == true);
						
						Server.getInstance().pingPlayerReady();
					}
					else {
						reply = new MessageInitReject();
					}
					sendMessage(reply);
					
					break;
				case READY:
					//need to do it like this - need to wait until server has finished pinging
					while (Server.getInstance().getServerGame().isPinging == true);

					boolean ready = (boolean) message.getValue();
					
					player.ready = ready;
					Server.getInstance().pingPlayerReady();
					break;
				case START_RESOURCE_REGION_BIDDING:
					Coords regionCoords = (Coords) message.getValue();
					Server.getInstance().getServerGame().setRegionBiddable(regionCoords);
					break;
				case RESOURCE_REGION_BID: 
					ResourceRegionBid regionBid = (ResourceRegionBid) message.getValue();
					Server.getInstance().getServerGame().addRegionBid(regionBid);
					break;
				case REQUEST_CONTRACT: 
					//Do contract calculations
					ContractRequest request = (ContractRequest) message.getValue();
					Contract contract = Server.getInstance().getServerGame().calculateContract(request);
					sendMessage(new MessageContractRequestAnswer(new ContractRequestAnswer(request.coords, contract)));
					break;
				case CONFIRM_CONTRACT:
					ContractRequestAnswer confirmation = (ContractRequestAnswer) message.getValue();
					Server.getInstance().getServerGame().confirmContract(confirmation);
					break;
				default:
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					if (in != null)
						in.close();
					if (out != null)
						in.close();
					if (clientSocket != null)
						clientSocket.close();
				}
				catch (Exception ex) {}
				return;
			} 
		}
		while (reading == false) {
			
		}
		handleMessage();
	}

	public void sendMessage(Message message)	{
		try {
			out.writeObject(message);
			out.flush();
			out.reset();
		} catch (Exception e) {
			System.out.println("Error sending a message to client:");
			System.out.println("Type: " + message.getType());
			e.printStackTrace();
		}
	}
	
	public void finishGame() {
		//sendFinishedMessage
		try {
			if (clientSocket != null)
				clientSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
