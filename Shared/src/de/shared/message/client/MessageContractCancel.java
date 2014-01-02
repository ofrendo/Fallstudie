package de.shared.message.client;

import de.shared.map.relation.ContractRequestAnswer;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageContractCancel implements Message {

	private static final long serialVersionUID = 8001445320284854395L;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.CANCEL_CONTRACT;
	}

	private ContractRequestAnswer cancellation;
	
	public MessageContractCancel(ContractRequestAnswer cancellation) {
		this.cancellation = cancellation;
	}
	
	@Override
	public Object getValue() {
		return cancellation;
	}

}
