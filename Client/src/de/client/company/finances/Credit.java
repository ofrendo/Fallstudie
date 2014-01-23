package de.client.company.finances;

import de.client.company.*;

public class Credit {
	private Company company;
	private CreditType creditType;
	private double amountLeft;
	private double interestsPerQuarter = 0;	// the interests for each quarter this year
	
	public Credit(CreditType creditType, Company company){
		this.creditType = creditType;
		this.amountLeft = creditType.amount;
		this.company = company;
		recalcInterestsPerQuarter();
	}
	
	public void recalcInterestsPerQuarter() {
		interestsPerQuarter = amountLeft * creditType.rate / 4;	// payments divided to each quarter
																// interests calculated on the start value; also you already repay quarter per quarter
	}

	public CreditType getCreditType(){
		return creditType;
	}
	
	public double getAmountLeft(){
		return amountLeft;
	}
	
	public double getInterestsPerQuarter(){
		return interestsPerQuarter;
	}
	
	public void payQuarter(){
		double repayment = creditType.amount / creditType.runtime;
		if(repayment > amountLeft){
			repayment = amountLeft;
		}
		double totalPayment = interestsPerQuarter + repayment;
		company.setMoney(company.getMoney() - totalPayment);
		amountLeft -= repayment;
	}
}
