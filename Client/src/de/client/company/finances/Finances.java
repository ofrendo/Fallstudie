package de.client.company.finances;

import java.util.ArrayList;

import de.client.company.*;

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
		// perform payment for credits
		for(Credit credit: credits){
			credit.payQuarter();
		}
		// 
		balance.nextRound();
	}
	
	public void nextYear(){
		balance.nextYear();
		// update credit interests
		for(Credit credit: credits){
			credit.recalcInterestsPerQuarter();
		}
	}

}