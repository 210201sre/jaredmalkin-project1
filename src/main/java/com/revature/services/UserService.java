package com.revature.services;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Role;
//import com.revature.exceptions.LoginUserFailedException;
//import com.revature.exceptions.RegisterUserFailedException;
import com.revature.models.User;
import com.revature.repositories.UserDAO;

@Service
public class UserService {
	
	private static final Logger log = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private HttpServletRequest req;
	
	@Autowired
	private UserDAO userDAO;
	
	public List<User> getAllUsers(){
		MDC.put("event", "GetAllUsers");
		log.info("Getting all Users");
		return userDAO.findAll();
	}

	public void register(@Valid User u) {
		// TODO Auto-generated method stub
		MDC.put("event", "Register");
		log.info("Registering new User");
		userDAO.save(u);
		log.info("Successfully registered User");
	}
	
	public User findById(int userId) {
		MDC.put("userId", Integer.toString(userId));
		User u = userDAO.findById(userId).orElse(null);
		if(u != null) {
			log.info("We successfully found User");
		}
		else {
			log.error("We failed to find User");
		}
		MDC.clear();
		return u;
	}
	
	public User changeRole(int userId, Role role) {
		userDAO.changeRole(userId, role);
		User u = findById(userId);
		u.setRole(role);
		return u;
	}
	
	public User login(String username, String password) {
		MDC.put("event", "login");
		User u = userDAO.findByUsername(username)
							.orElseThrow(() -> new UserNotFoundException(String.format("No User with username = %s", username)));
		
		if(u.getPassword().equals(password)) {
			HttpSession session = req.getSession();
			session.setAttribute("currentUser", u);
			log.info("User successfully logged in");
			MDC.clear();
			return u;
		} 
		
		MDC.put("wrongPassword", password);
		log.error("Incorrect password");
		MDC.clear();
		return null;
	}
	
	public void logout() {
		HttpSession session = req.getSession(false);
		
		if(session == null) {
			// No one was logged in
			return;
		}
		
		session.invalidate();
		log.info("Logged out");
		MDC.clear();
	}
}
