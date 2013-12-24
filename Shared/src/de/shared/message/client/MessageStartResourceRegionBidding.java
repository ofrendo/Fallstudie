package de.shared.message.client;

import de.shared.map.region.Coords;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageStartResourceRegionBidding implements Message {

	private static final long serialVersionUID = 2407821341195279289L;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.START_RESOURCE_REGION_BIDDING;
	}

	private Coords coords;
	
	public MessageStartResourceRegionBidding(Coords coords) {
		this.coords = coords;
	}
	
	@Override
	public Object getValue() {
		return coords;
	}

	
	
}
