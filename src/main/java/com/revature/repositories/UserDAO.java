package com.revature.repositories;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.revature.models.Role;
import com.revature.models.User;

public interface UserDAO extends JpaRepository<User, Integer>{
	public Optional <User> findByUsername(String username);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE public.users SET role = (:role) WHERE id = (:id);", nativeQuery = true)
	public void changeRole(@Param("id") int id, @Param("role") Role role);
	
	
}
