package de.client.company.finances;

import de.client.company.*;

public class Credit {
	private Company company;
	private CreditType creditType;
	private double amountLeft;
	private double lastInterest = 0;	// the last recently paid interest
	
	public Credit(CreditType creditType, Company company){
		this.creditType = creditType;
		this.amountLeft = creditType.amount;
		this.company = company;
	}
	
	public CreditType getCreditType(){
		return creditType;
	}
	
	public double getAmountLeft(){
		return amountLeft;
	}
	
	public double getLastInterest(){
		return lastInterest;
	}
	
	public void payQuarter(){
		lastInterest = amountLeft * creditType.rate;
		double repayment = creditType.amount / creditType.runtime;
		if(repayment > amountLeft){
			repayment = amountLeft;
		}
		double totalPayment = lastInterest + repayment;
		company.setMoney(company.getMoney() - totalPayment);
		amountLeft -= repayment;
	}
}
