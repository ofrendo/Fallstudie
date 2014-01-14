package de.shared.map.generate;

public enum MapTypeHexagon implements MapType {
		SMALL(4, 4),
		NORMAL(5, 5),
		LARGE(6, 6);
		
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
		public int getAmountRows() {
			return hexagonLength * 2 -1;
		}

		@Override
		public int getNumberTiles() {
			return numberTiles;
		}

		@Override
		public int getNumberCities() {
			return numberCities;
		}

		public int getAmountTilesForRow(int rowIndex) {
			if (rowIndex == 0) 
				return hexagonLength;
			
			int middleRowIndex = getAmountRows() / 2;
			int maxRowIndex = getAmountRows() - 1;
			
			if (rowIndex <= middleRowIndex) {
				return 1 + getAmountTilesForRow(rowIndex-1);
			}
			else if (rowIndex <= maxRowIndex) {
				return -1 + getAmountTilesForRow(rowIndex-1);
			}
			else {
				return getAmountTilesForRow(maxRowIndex);
			}
		}
		
		public int getMaxAmountTilesForRow() {
			return getAmountTilesForRow( getLongestRowIndex() );
		}
		
		public int getLongestRowIndex() {
			return getAmountRows() / 2;
		}
		
	}