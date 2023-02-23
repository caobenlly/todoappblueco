package com.example.todoapp.entity;

import lombok.Data;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
@Data
@Entity
@Table(name = "users")

public class User implements  Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

//	@Formula("concat(first_name, ' ', last_name)")
//	private String fullName;

	@Enumerated(EnumType.ORDINAL)
	@Column(name = "status", nullable = true)
	private UserStatus status = UserStatus.NOT_ACTIVE;


}