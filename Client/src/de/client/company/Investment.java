package de.client.company;

public class Investment {
	private double purchaseValue;
	private int depreciationYears;
	private int depreciationYearsLeft;
	private double bookValue;
	private double lastDepreciation = 0;
	
	public Investment(double purchaseValue, int depreciationYears){
		this.purchaseValue = purchaseValue;
		this.bookValue = purchaseValue;
		this.depreciationYears = depreciationYears;
		this.depreciationYearsLeft = depreciationYears;
	}
	
	public void performDepreciation(){
		if(depreciationYearsLeft > 0){
			double depreciation = purchaseValue / depreciationYears;
			if(bookValue > depreciation){
				lastDepreciation = depreciation;
			} else {
				lastDepreciation = bookValue - 1;
			}
			bookValue -= lastDepreciation;
			depreciationYearsLeft--;
		} else {
			lastDepreciation = 0;
		}
	}

	public int getDepreciationYearsLeft() {
		return depreciationYearsLeft;
	}

	public double getBookValue() {
		return bookValue;
	}

	public double getLastDepreciation() {
		return lastDepreciation;
	}
}
