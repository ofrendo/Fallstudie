package de.shared.map.relation;

import java.io.Serializable;
import de.shared.map.region.*;

public class ContractRequestAnswer implements Serializable {

	private static final long serialVersionUID = -1104524115696889166L;

	public final Coords coords;
	public final Contract contract;
	
	public ContractRequestAnswer(Coords coords, Contract contract) {
		this.coords = coords;
		this.contract = contract;
	}
	
	
	
}
