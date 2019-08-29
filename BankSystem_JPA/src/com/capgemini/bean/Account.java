package com.capgemini.bean;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account {
	@Id
	@GeneratedValue
	private long accountNo;
	private String firstName;
	private String LastName;
	private long mobileNo;
	private String gender;
	private double balance;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "account")
	private List<Transaction> transactions = new ArrayList<>();

	public long getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(long accountNo) {
		this.accountNo = accountNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public long getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(long mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "\n Account No=" + accountNo + ", First Name=" + firstName + ", Last Name=" + LastName + ", Mobile No="
				+ mobileNo + ", Gender=" + gender + ", Balance=" + balance;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	public Account(long accountNo, String firstName, String lastName, long mobileNo, String gender, double balance,
			List<Transaction> transactions) {
		super();
		this.accountNo = accountNo;
		this.firstName = firstName;
		this.LastName = lastName;
		this.mobileNo = mobileNo;
		this.gender = gender;
		this.balance = balance; // this.transactions = transactions;
	}

	public Account() {
		super();
	}
}
