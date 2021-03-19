package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import com.revature.services.AccountService;
import com.revature.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AccountService accountService;
	
	@PostMapping("/users")
	public ResponseEntity<String> register(@Valid @RequestBody User u){ //Working
		userService.register(u);
		MDC.clear();
		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}
	
	@GetMapping("/users")
	public ResponseEntity<List<User>> getAllUsers(){ //Working
		List<User> users = userService.getAllUsers();
		if(users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		MDC.clear();
		return ResponseEntity.ok(users);
	}
	
	@GetMapping("/accounts")
	public ResponseEntity<List<Account>> getAllAccounts(){ //Working
		List<Account> accounts = accountService.getAllAccounts();
		if(accounts.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		MDC.clear();
		return ResponseEntity.ok(accounts);
	}
	
	@PostMapping(path = "/accounts/user/{userId}")
	public ResponseEntity<String> createAccount(@PathVariable("userId") int userId){
		//get User by owner_id, a.setOwner(user)
		//double balanceDouble = Double.parseDouble(balance);
		Account a = new Account();
//		a.setBalance(balance);
		User u = userService.findById(userId);
		a.setOwner(u);
		accountService.createAccount(a);
		MDC.clear();
		return new ResponseEntity<>("Account " +a.getId()+ " created successfully", HttpStatus.CREATED);
	} //working
	
	@PutMapping(path = "/accounts/{accountId}/deposit/{amount}")
	public ResponseEntity<String> deposit(@PathVariable int accountId, @PathVariable double amount){
		double newBalance = accountService.deposit(accountId, amount);
		MDC.clear();
		return new ResponseEntity<>("Account " + accountId + " has a new balance of: " + newBalance + " after a deposit of " + amount, HttpStatus.ACCEPTED);
	} //working
	
	@PutMapping(path = "/accounts/{accountId}/withdraw/{amount}")
	public ResponseEntity<String> withdraw(@PathVariable int accountId, @PathVariable double amount){
		double amountWithdrawn = accountService.withdraw(accountId, amount);
		MDC.clear();
		double newBalance = accountService.findById(accountId).getBalance() - amount;
		return new ResponseEntity<>("Account " + accountId + " has a new balance of: " + newBalance + " after a withdraw of " + amountWithdrawn, HttpStatus.ACCEPTED);
	} //working
	
	@PutMapping(path = "/accounts/transfer/{amount}/from/{accountId1}/to/{accountId2}")
	public ResponseEntity<String> transfer(@PathVariable int accountId1, @PathVariable int accountId2, @PathVariable double amount){
		double amountWithdrawn = accountService.withdraw(accountId1, amount);
		double newBalance1 = accountService.findById(accountId1).getBalance() - amountWithdrawn;
		double newBalance2 = accountService.deposit(accountId2, amountWithdrawn);
		MDC.clear();
		return new ResponseEntity<>("Amount: " + amountWithdrawn + " has been transferred from Account " + accountId1 + " to Account " + accountId2 + ".\n"
				+ "Account " + accountId1 + " now has a balance of: " + newBalance1 + " , and Account " + accountId2 + " now has a balance of: " + newBalance2, HttpStatus.ACCEPTED);
	} //working
	
	@Authorized(allowedRoles = {Role.ADMIN})
	@PostMapping(path = "/users/admin")
	public ResponseEntity<String> registerAdmin(@RequestBody User u){
		u.setRole(Role.ADMIN);
		userService.register(u);
		MDC.clear();
		return new ResponseEntity<>("Registered User as ADMIN", HttpStatus.CREATED);
	}
	
	@Authorized(allowedRoles = {Role.ADMIN})
	@DeleteMapping(path = "/accounts/{accountId}")
	public ResponseEntity<String> deleteAccount(@PathVariable int accountId){
		accountService.deleteById(accountId);
		return new ResponseEntity<>("Deleted Account", HttpStatus.ACCEPTED);
	}

}
