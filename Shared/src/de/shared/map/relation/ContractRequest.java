package de.shared.map.relation;

import java.io.Serializable;

import de.shared.game.Player;
import de.shared.map.region.Coords;

public class ContractRequest implements Serializable {

	private static final long serialVersionUID = -3162842962703723594L;
	
	public Player player;
	public Coords coords;

	public final int maxCustomers;
	public final double amountMoneyPerCustomer;
	public final double popularity;
	public final double awareness;
	
	public ContractRequest(Player player, Coords coords, int maxCustomers, double amountMoneyPerCustomer, double popularity, double awareness) {
		this.player = player;
		this.coords = coords;

		this.maxCustomers = maxCustomers;
		this.amountMoneyPerCustomer = amountMoneyPerCustomer;
		this.popularity = popularity;
		this.awareness = awareness;
	}
	
}
