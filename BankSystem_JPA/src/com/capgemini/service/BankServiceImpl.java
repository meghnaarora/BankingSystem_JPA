package com.capgemini.service;

import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import com.capgemini.bean.Account;
import com.capgemini.bean.Transaction;
import com.capgemini.dao.BankDaoImpl;
import com.capgemini.dao.IBankDao;
import com.capgemini.exception.BSPException;

public class BankServiceImpl implements IBankService {

	IBankDao dao = new BankDaoImpl();

	EntityManagerFactory factory = Persistence.createEntityManagerFactory("JPA-PU");
	EntityManager em = factory.createEntityManager();

	Scanner scanner = new Scanner(System.in);

	@Override
	public boolean validateName(String vehicleName) throws BSPException {
		/*
		 * String ptn = "[A-Z]{1}[a-zA-Z] {1,}"; boolean status = false; if
		 * (!Pattern.matches(ptn, firstName)) throw new BSPException(); else status =
		 * true; return status;
		 */

		boolean resultFlag = false;
		String nameRegEx = "[A-Z]{1}[a-zA-Z ]{1,}";

		if (!Pattern.matches(nameRegEx, vehicleName)) {
			throw new BSPException("first letter should be capital and min length 2");
		} else {
			resultFlag = true;
		}
		return resultFlag;
	}

	@Override
	public boolean validateMobile(Long mobile) throws BSPException {
		String sMobile = Long.toString(mobile);
		boolean resultFlag = false;
		String nameRegEx = "^[0-9 ]{10}$";

		if (!Pattern.matches(nameRegEx, sMobile)) {
			throw new BSPException("Enter 10 digits");
		} else {
			resultFlag = true;
		}
		return resultFlag;
	}

	@Override
	public boolean validateGender(String gender) throws BSPException {
		boolean status = false;
		if (gender.equalsIgnoreCase("m") || gender.equalsIgnoreCase("f")) {
			status = true;
		} else {
			throw new BSPException("Enter m/f");
		}
		return status;
	}

	public boolean ifAccountExists(long accountNo) throws BSPException {

		boolean status = false;

		em.getTransaction().begin();
		Account a = em.find(Account.class, accountNo);
		em.getTransaction().commit();
		if (a == null)
			status = false;
		else
			status = true;

		if (status == false)
			throw new BSPException("No such account exists");
		return status;
	}

	@Override
	public long addAccount(Account account) {
		return dao.addAccount(account);
	}

	@Override
	public double showBalance(long accountNo) throws BSPException {
		double balance = 0.0;
		if (ifAccountExists(accountNo))
			balance = dao.showBalance(accountNo);
		else
			throw new BSPException();
		return balance;
	}

	@Override
	public boolean withdraw(long accountNo) throws BSPException {
		boolean status = false;
		// check if accountNo exist in dao

		if (ifAccountExists(accountNo)) {
			System.out.print("Enter amount to be withdrawn:\t");
			double amount = scanner.nextDouble();
			if (dao.withdraw(accountNo, amount))
				status = true;
		}
		return status;
	}

	@Override
	public boolean deposit(long accountNo) throws BSPException {
		boolean status = false;

		if (ifAccountExists(accountNo)) {
			System.out.print("Enter amount to be deposit:\t");
			double amount = scanner.nextDouble();
			if (dao.deposit(accountNo, amount))
				status = true;
		}
		return status;
	}

	@Override
	public List<Account> showAllAccounts() {
		return dao.showAllAccounts();
	}

	@Override
	public boolean fundTransfer(long senderAccNo, double amount, long receiverAccNo) throws BSPException {
		boolean status = false;
		if (ifAccountExists(senderAccNo) && ifAccountExists(receiverAccNo) && amount != 0)
			status = dao.fundTransfer(senderAccNo, amount, receiverAccNo);
		else
			throw new BSPException("Enter valid account numbers");
		return status;
	}

	@Override
	public List<Transaction> showAllTransactions(long accountNo) throws BSPException {
		if (ifAccountExists(accountNo)) {
			return dao.showAllTransactions(accountNo);
		}
		return null;
	}
}
