package de.shared.map.generate;

public enum MapTypeRect implements MapType {
	SMALL(10, 6, 4),
	NORMAL(11, 8, 5),
	LARGE(12, 9, 12);
	
	public int lengthRow;
	public int amountRows;
	public int numberTiles;
	public int numberCities;
	MapTypeRect(int lengthRow, int amountRows, int numberCities) {
		this.lengthRow = lengthRow;
		this.amountRows = amountRows;
		this.numberTiles = lengthRow * amountRows;
		this.numberCities = numberCities;
	}
	
	@Override
	public int getNumberTiles() {
		return numberTiles;
	}
	@Override
	public int getNumberCities() {
		return numberCities;
	}
}
