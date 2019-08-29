package com.capgemini.service;

import java.util.List;

import com.capgemini.bean.Account;
import com.capgemini.bean.Transaction;
import com.capgemini.exception.BSPException;

public interface IBankService {
	public boolean validateName(String firstName) throws BSPException;

	public boolean validateMobile(Long mobile) throws BSPException;

	public boolean validateGender(String gender) throws BSPException;

	public long addAccount(Account account);

	public double showBalance(long accountNo) throws BSPException;

	public boolean withdraw(long accountNo) throws BSPException;
	
	public List<Account> showAllAccounts();

	public boolean deposit(long accountNo) throws BSPException;

	public boolean fundTransfer(long senderAccNo, double amount, long receiverAccNo) throws BSPException;

	public List<Transaction> showAllTransactions(long accountNo) throws BSPException;
}
