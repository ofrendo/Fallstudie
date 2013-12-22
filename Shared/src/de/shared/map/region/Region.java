package de.shared.map.region;

import java.io.Serializable;

public abstract class Region implements Serializable {
	private static final long serialVersionUID = 6009672985764339480L;
	public Coords coords;
	
	public Region(int regionIDX, int regionIDY) {
		this.coords = new Coords(regionIDX, regionIDY);
	}
}
