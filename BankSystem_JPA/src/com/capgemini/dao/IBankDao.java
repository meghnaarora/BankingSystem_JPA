package com.capgemini.dao;

import java.util.List;

import com.capgemini.bean.Account;
import com.capgemini.bean.Transaction;
import com.capgemini.exception.BSPException;

public interface IBankDao {

	public long addAccount(Account account);

	public double showBalance(long accountNo);

	public boolean withdraw(long accNo, double amount);

	public boolean deposit(long accountNo, double amount);

	public List<Account> showAllAccounts();

	public boolean fundTransfer(long senderAccNo, double amount, long receiverAccNo) throws BSPException;

	public List<Transaction> showAllTransactions(long accountNo) throws BSPException;
}
