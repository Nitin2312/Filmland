package com.entertainment.filmland.model;



import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
@Embeddable
public class SubscriberService{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscriberServiceIdentifier", updatable = false, nullable = false)
	private long subscriberServiceIdentifier;
	@Column(name = "email", nullable = false)
	private String email;
   @Column(name = "serviceId", nullable = false)
	private long serviceId;
   @Column(name="startdate",nullable =false)
	private java.sql.Timestamp startDate;
   @Column(name="enddate",nullable =false)
	private java.sql.Timestamp enddate;
  
   
}
