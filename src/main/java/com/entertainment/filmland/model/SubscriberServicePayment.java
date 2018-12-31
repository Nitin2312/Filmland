package com.entertainment.filmland.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Embeddable
public class SubscriberServicePayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subscriberPaymentIdentifier", updatable = false, nullable = false)
	private long subscriberPaymentIdentifier;
	@Column(name = "subscriberServiceIdentifier", nullable = false)
	private long subscriberServiceIdentifier;
   @Column(name = "paymentdate", nullable = false)
	private Timestamp paymentdate;
}
