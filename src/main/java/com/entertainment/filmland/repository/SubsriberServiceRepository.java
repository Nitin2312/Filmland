package com.entertainment.filmland.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.entertainment.filmland.model.SubscriberService;
@Repository
public interface SubsriberServiceRepository extends JpaRepository<SubscriberService, Long>{

	public List<SubscriberService> findByEmail(String email);
	
	public Optional<SubscriberService> findByServiceId(long serviceId);
	
	public Optional<SubscriberService> findByEmailAndServiceId(String email,long serviceId);
	
}
