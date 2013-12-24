package de.shared.message.client;

import de.shared.map.relation.ContractRequest;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageRequestContract implements Message {

	private static final long serialVersionUID = -5544468266991647524L;
	
	private ContractRequest contractRequest;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.REQUEST_CONTRACT;
	}

	public MessageRequestContract(ContractRequest request) {
		this.contractRequest = request;
	}
	
	@Override
	public Object getValue() {
		return contractRequest;
	}

}
