package de.client.company.finances;

public enum CreditType {
	CREDIT_1(100000000, 5, 12),
	CREDIT_2(500000000, 5, 20),
	CREDIT_3(1000000000, 5, 40),
	
	TEST_1(500000, 5, 8),		// values for unittest
	TEST_2(1000000, 10, 12);	// do not change!!!
	
	public double rate;	// per year
	public double amount;
	public int runtime;	// in quarters
	
	CreditType(double amount, double rate, int runtime){
		this.runtime = runtime;
		this.rate = rate;
		this.amount = amount;
	}
}
