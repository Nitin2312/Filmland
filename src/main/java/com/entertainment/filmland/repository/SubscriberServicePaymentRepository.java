package com.entertainment.filmland.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entertainment.filmland.model.SubscriberServicePayment;

@Repository
public interface SubscriberServicePaymentRepository extends JpaRepository<SubscriberServicePayment, Long>{
}
