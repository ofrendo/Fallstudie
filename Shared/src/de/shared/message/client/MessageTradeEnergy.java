package de.shared.message.client;

import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageTradeEnergy implements Message {

	private static final long serialVersionUID = -2488389858394871238L;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.TRADE_ENERGY;
	}
	
	private double amount;
	
	public MessageTradeEnergy(double amount) {
		this.amount = amount;
	}

	@Override
	public Object getValue() {
		return amount;
	}

}
