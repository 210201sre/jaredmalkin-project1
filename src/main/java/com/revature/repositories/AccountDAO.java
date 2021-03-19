package com.revature.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.Account;


public interface AccountDAO extends JpaRepository<Account, Integer>{
	public Optional<Account> findById(int id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE public.accounts SET balance = balance + (:amount) WHERE id = (:id);", nativeQuery = true)
	public void deposit(@Param("id") int id, @Param("amount") double amount);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE public.accounts SET balance = balance - (:amount) WHERE id = (:id);", nativeQuery = true)
	public void withdraw(@Param("id") int id, @Param("amount") double amount);
}
