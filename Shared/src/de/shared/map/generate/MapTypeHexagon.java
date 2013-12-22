package de.shared.map.generate;

public enum MapTypeHexagon implements MapType {
		SMALL(4, 3),
		NORMAL(5, 4),
		LARGE(6, 5);
		
		int hexagonLength;
		int numberTiles;
		int numberCities;
		MapTypeHexagon(int hexagonLength, int numberCities) {
			this.hexagonLength = hexagonLength;
			this.numberTiles = getNumTilesHexa(hexagonLength);
			this.numberCities = numberCities;
		}
		
		private static int getNumTilesHexa(int length) {
			if (length == 1) 
				return 1;
			
			length--;
			return length*6  + getNumTilesHexa(length);
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