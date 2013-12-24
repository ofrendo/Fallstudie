package de.shared.map.region;

import java.io.Serializable;

public class Coords implements Serializable {
	private static final long serialVersionUID = 3060117471142302695L;

	public final int x;
	public final int y;
	
	public Coords(int regionIDX, int regionIDY) {
		this.x = regionIDX;
		this.y = regionIDY;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Coords) {
			Coords otherCoords = (Coords) o;
			return this.x == otherCoords.x && this.y == otherCoords.y;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "x: " + x + " y: " + y;
	}
}
