package de.client.company;

public enum CreditType {
	CREDIT_1(500000, 5, 12),
	CREDIT_2(1000000, 5, 20),
	CREDIT_3(3000000, 5, 40);
	
	public double rate;	// per year
	public int amount;
	public int runtime;	// in quarters
	
	CreditType(int amount, double rate, int runtime){
		this.runtime = runtime;
		this.rate = rate;
		this.amount = amount;
	}
}
