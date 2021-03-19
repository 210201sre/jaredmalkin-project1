package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.models.Account;
import com.revature.models.User;
import com.revature.repositories.AccountDAO;
import com.revature.repositories.UserDAO;


@Service
public class AccountService {

	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private AccountDAO accountDAO;
	
	public List<Account> getAllAccounts(){
		MDC.put("event", "GetAllAccounts");
		log.info("Getting all Accounts");
		return accountDAO.findAll();
	}
	
	public void createAccount(Account a) {
		MDC.put("event", "CreateAccount");
		log.info("Creating Account");
		accountDAO.save(a);
		log.info("Created Account");
	}
	
	public Account findById(int accountId) {
		MDC.put("accountId", Integer.toString(accountId));
		Account a = accountDAO.findById(accountId).orElse(null);
		if(a != null) {
			log.info("We successfully found Account");
		}
		else {
			log.error("We failed to find Account");
		}
		MDC.clear();
		return a;
	}
	
	public double deposit(int accountId, double amount) {
		MDC.put("accountId", Integer.toString(accountId));
		accountDAO.deposit(accountId, amount);
		log.info("We successfully deposited into Account");
		MDC.clear();
		double newBalance = findById(accountId).getBalance();
		return newBalance;
	}
	
	public double withdraw(int accountId, double amount) {
		MDC.put("accountId", Integer.toString(accountId));
		double balance = findById(accountId).getBalance();
		if(balance < amount) {
			amount = balance;
		}
		accountDAO.withdraw(accountId, amount);
		log.info("We successfully withdrew from Account");
		MDC.clear();
		return amount;
	}
	
	public void deleteById(int accountId) {
		MDC.put("event", "deleteAccount");
		accountDAO.deleteById(accountId);
		MDC.clear();
	}
}
