package de.shared.message.client;

import de.shared.map.region.FinishedBuilding;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageBuildingFinished implements Message {
	
	FinishedBuilding finishedBuilding;
	
	private static final long serialVersionUID = 1L;

	public MessageType getType() {

		return MessageTypeToServer.BUILDING_FINISHED;
	}

	public MessageBuildingFinished(FinishedBuilding finishedBuilding)
	{
		this.finishedBuilding = finishedBuilding;
	}
	
	public Object getValue() {

		return this.finishedBuilding;
	}

}
