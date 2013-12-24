package de.shared.message.client;

import de.shared.map.relation.ContractRequestAnswer;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToServer;

public class MessageContractConfirm implements Message {

	private static final long serialVersionUID = 5888414026664595400L;

	@Override
	public MessageType getType() {
		return MessageTypeToServer.CONFIRM_CONTRACT;
	}

	private ContractRequestAnswer answer;
	public MessageContractConfirm(ContractRequestAnswer answer) {
		this.answer = answer;
	}
	
	@Override
	public Object getValue() {
		// TODO Auto-generated method stub
		return answer;
	}

}
