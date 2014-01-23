package de.client.company.finances;

public enum CreditType {
	CREDIT_1(500000, 5, 12),
	CREDIT_2(1000000, 5, 20),
	CREDIT_3(3000000, 5, 40),
	
	TEST_1(500000, 5, 8),
	TEST_2(1000000, 10, 12);
	
	public double rate;	// per year
	public double amount;
	public int runtime;	// in quarters
	
	CreditType(double amount, double rate, int runtime){
		this.runtime = runtime;
		this.rate = rate;
		this.amount = amount;
	}
}
