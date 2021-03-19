package com.revature.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", schema = "public")
@Data @AllArgsConstructor @NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, unique = true, updatable = false)
	private int id;
	
	@Column(nullable = false, unique = true, updatable = false)
	@Length(min = 1)
	@NotBlank
	@Pattern(regexp = "[a-zA-Z][a-zA-Z0-9]*")
	private String username;
	
	@Column(nullable = false)
	@NotEmpty
	@NotBlank
	private String password;
	
	//add role
	@Column(nullable = false)
	@Enumerated (EnumType.STRING)
	private Role role = Role.CUSTOMER;
	
	//-----STUFF TO MAYBE IMPLEMENT-------
	//@Length(min = 1)
	//private String firstName;
	//private String lastName;
	//
	//@Email
	//private String email;
	//
	//ROLE????
	
	//ManyToMany? idk
	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, mappedBy = "owner", fetch = FetchType.EAGER)
	private Set<Account> accounts;
	
	
	
}
