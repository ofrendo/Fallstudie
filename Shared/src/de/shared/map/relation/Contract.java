package de.shared.map.relation;

import java.io.Serializable;

import de.shared.game.Player;
import de.shared.map.region.Coords;

public class Contract implements Serializable {
	private static final long serialVersionUID = -8190051619971410891L;

	private Player player;
	public Coords coords;
	
	public int amountCustomer;
	public double amountEnergyNeeded;
	public double amountEnergySupplied;
	public double amountMoneyPerQuarter; 
	public double amountMoneyPerCustomer; //for example 0.39
	
	public Contract(Player player, Coords coords, int amountCustomer, double amountEnergyNeeded, double amountMoneyPerCustomer) {
		this.player = player;
		this.coords = coords;
		
		this.amountCustomer = amountCustomer;
		this.amountEnergyNeeded = amountEnergyNeeded;
		this.amountMoneyPerCustomer = amountMoneyPerCustomer;
		this.amountMoneyPerQuarter = amountMoneyPerCustomer * amountCustomer;
	}
	
	public boolean isOwnPlayer(Player player) {
		return this.player.equals(player);
	}

	public Player getPlayer() {
		return player;
	}
	
}
