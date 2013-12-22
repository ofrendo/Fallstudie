package de.server.message;
import de.shared.message.Message;
import de.shared.message.MessageType;
import de.shared.message.MessageTypeToClient;
import de.shared.map.relation.*;

public class MessageContractRequestAnswer implements Message {

	private static final long serialVersionUID = -16239499854239431L;

	@Override
	public MessageType getType() {
		return MessageTypeToClient.CONTRACT_REQUEST_ANSWER;
	}

	private ContractRequestAnswer contractRequestAnswer;
	
	public MessageContractRequestAnswer(ContractRequestAnswer contractRequestAnswer) {
		this.contractRequestAnswer = contractRequestAnswer;
	}
	
	@Override
	public Object getValue() {
		return contractRequestAnswer;
	}

}
