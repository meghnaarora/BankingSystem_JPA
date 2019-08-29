package com.capgemini.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import com.capgemini.bean.Account;
import com.capgemini.bean.Transaction;
import com.capgemini.exception.BSPException;

public class BankDaoImpl implements IBankDao {

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-PU");
	EntityManager em = factory.createEntityManager();

	Scanner scanner = new Scanner(System.in);

	@Override
	public long addAccount(Account account) {

		em.getTransaction().begin();
		em.persist(account);
		em.getTransaction().commit();

		return account.getAccountNo();

	}

	@Override
	public double showBalance(long accountNo) {
		em.getTransaction().begin();
		Account a = em.find(Account.class, accountNo);
		em.getTransaction().commit();
		return a.getBalance();
	}

	@Override
	public boolean withdraw(long accNo, double amount) {
		em.getTransaction().begin();
		boolean status = false;
		// check if accountNo exist in dao
		Account a = null;
		if (accNo != 0) {

			a = em.find(Account.class, accNo);
			double balance = a.getBalance();

			double newBalance = balance - amount;
			a.setBalance(newBalance);

			status = true;
		}

		if (status == true) {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			String strDate = dateFormat.format(date);

			Transaction transaction = new Transaction(0, "debit", strDate, amount, a);
			long transactionId = (long) (Math.random() * 1000000L);
			transaction.setTransactionId(transactionId);
			a.getTransactions().add(transaction);
			transaction.setAccount(a);
			em.persist(transaction);
			em.persist(a);
			em.getTransaction().commit();
			System.out.println("Transaction successful. TransactionId:\t" + transactionId);
		}

		return status;
	}

	@Override
	public boolean deposit(long accNo, double amount) {
		boolean status = false;
		Account a = null;

		em.getTransaction().begin();
		if (accNo != 0) {

			a = em.find(Account.class, accNo);
			double balance = a.getBalance();

			double newBalance = balance + amount;
			a.setBalance(newBalance);

			status = true;
		}
		if (status == true) {
			Date date = Calendar.getInstance().getTime();
			DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
			String strDate = dateFormat.format(date);

			Transaction transaction = new Transaction(0, "credit", strDate, amount, a);
			long transactionId = (long) (Math.random() * 1000000L);
			transaction.setTransactionId(transactionId);
			a.getTransactions().add(transaction);
			transaction.setAccount(a);
			em.persist(transaction);
			em.persist(a);
			em.getTransaction().commit();
			System.out.println("Transaction successful. TransactionId:\t" + transactionId);
		}
		return status;
	}

	@Override
	public List<Account> showAllAccounts() {
		em.getTransaction().begin();
		TypedQuery<Account> query = em.createQuery("from Account", Account.class);
		em.getTransaction().commit();

		return query.getResultList();
	}

	@Override
	public boolean fundTransfer(long senderAccNo, double amount, long receiverAccNo) throws BSPException {
		boolean status = false;
		if (senderAccNo == receiverAccNo)
			throw new BSPException("Fund transfer to self account not possible");

		if (withdraw(senderAccNo, amount)) {
			status = deposit(receiverAccNo, amount);
		}
		return status;
	}

	@Override
	public List<Transaction> showAllTransactions(long accountNo) throws BSPException {
		em.getTransaction().begin();
		Account a = em.find(Account.class, accountNo);
		em.getTransaction().commit();
		
		List<Transaction> transactions = a.getTransactions();
		
		if (transactions.isEmpty()) {
			//System.out.println("Khali hai");
			throw new BSPException("No Transactions for this account.");
		}
		return transactions;
	}
}
