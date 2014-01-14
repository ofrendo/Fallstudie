package de.client.company;

import java.util.ArrayList;

public class Finances extends Department {
	private Balance balance;
	private ArrayList<Credit> credits = new ArrayList<Credit>();
	
	public Finances(Company company){
		super("Finanzen", 0, company);
		this.balance = new Balance(company);
	}
	
	public void addCredit(CreditType creditType){
		credits.add(new Credit(creditType, getCompany()));
		getCompany().setMoney(getCompany().getMoney() + creditType.amount);
	}
	
	public double getDebtCapital(){
		double cap = 0;
		for(Credit credit: credits){
			cap += credit.getAmountLeft();
		}
		return cap;
	}
	
	public ArrayList<Credit> getCredits(){
		return credits;
	}
	
	public Balance getBalance(){
		return balance;
	}
	
	public void nextRound() {
		// recalculate costs
		this.setCosts(0);	// should the department for Finances (Balance, credits, etc.) cost anything?
		// perform payment for credits
		for(Credit credit: credits){
			credit.payQuarter();
		}
		// 
		balance.nextRound();
	}
	
	public void nextYear(){
		balance.nextYear();
	}

}
