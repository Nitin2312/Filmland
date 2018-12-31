package com.entertainment.filmland.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class FilmLandService {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "serviceId",updatable = false ,nullable = false, unique = true)
	private long serviceId;
	@Column(name = "name", nullable = false, unique = true)
	private String name;
	@Column(name = "availableContent", nullable = false)
	private long availableContent;
	@Column(name = "price", nullable = false)
	private long price;
}
