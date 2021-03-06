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
	
	public boolean isCreditWorthyFor(CreditType creditType){
		// can get a new credit if the debt equity ratio doesn't exceed 2:1
		return (creditType.amount + getDebtCapital()) / getBalance().getEquity() <= 2;
	}
	
	public boolean isInsolvent(){
		if(getDebtEquityRatio() >= 2 && getCompany().getMoney() <= 0){
			return true;
		}
		return false;
	}
	
	public double getDebtCapital(){
		double cap = 0;
		for(Credit credit: credits){
			cap += credit.getAmountLeft();
		}
		return cap;
	}
	
	public double getInterestsPerQuarterSum(){
		double interests = 0;
		for(Credit credit: credits){
			interests += credit.getInterestsPerQuarter();
		}
		return interests;
	}
	
	public ArrayList<Credit> getCredits(){
		return credits;
	}
	
	public double getDebtEquityRatio(){
		return getDebtCapital() / getBalance().getEquity();
	}
	
	public Balance getBalance(){
		return balance;
	}
	
	public void nextRound() {
		super.nextRound();
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
