package com.entertainment.filmland.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Subscriber {
	@Id
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	@Column(name = "password", nullable = false)
	private String password;
}
