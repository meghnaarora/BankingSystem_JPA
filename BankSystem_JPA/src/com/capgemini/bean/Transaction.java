package com.capgemini.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Transaction {
	@Id
	long transactionId;
	String transactionType;
	String transactionDate;
	double amount;
	@ManyToOne
	private Account account;

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Transaction() {
		super();
	}

	public Transaction(long transactionId, String transactionType, String transactionDate, double amount,
			Account account) {
		super();
		this.transactionId = transactionId;
		this.transactionType = transactionType;
		this.transactionDate = transactionDate;
		this.amount = amount;
		this.account = account;
	}

	@Override
	public String toString() {
		return "\n Account No=" + account.getAccountNo() + ", Transaction Id=" + transactionId + ", Transaction Type="
				+ transactionType + ", Transaction Date=" + transactionDate + ", Amount=" + amount;
	}

}
