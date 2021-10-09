package com.id.userproductservice.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
@Table(name = "users")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private int id;

	@Column(name="username", length = 40)
	private String username;

	@Email
	@Column(name="email", length = 25)
	private String email;

	@Column(name="gender", length = 6)
	private String gender;

	@Column(name="password", length = 200)
	private String password;

}
