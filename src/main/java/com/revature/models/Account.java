package com.revature.models;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "accounts", schema = "public")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(exclude = {"owner"}) @ToString(exclude = {"owner"})
public class Account {
	@Id
	@Column(nullable = false, unique = true, updatable = false)
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private int id;
	
	//is this one right? should i use manytoone on owner_id
	@ManyToOne
	@JoinColumn(name = "owner_id")
	@JsonBackReference
	private User owner;
	
	//is this columnDefinition right?
	@Column(columnDefinition = "decimal default 0.0")
	private double balance;
	
}
