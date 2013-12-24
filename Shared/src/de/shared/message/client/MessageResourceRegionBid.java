package de.shared.message.client;

import de.shared.game.Player;
import de.shared.map.region.Coords;
import de.shared.map.region.ResourceRegionBid;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageResourceRegionBid implements Message {

	private static final long serialVersionUID = 3745395127435656403L;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.RESOURCE_REGION_BID;
	}

	private ResourceRegionBid bid;
	
	public MessageResourceRegionBid(Coords coords, Player player, double amount) {
		this.bid = new ResourceRegionBid(coords, player, amount);
	}
	
	@Override
	public Object getValue() {
		return bid;
	}

}
