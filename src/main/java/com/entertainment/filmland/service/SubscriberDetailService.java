package com.entertainment.filmland.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entertainment.filmland.model.Subscriber;

@Service
public class SubscriberDetailService {

	private com.entertainment.filmland.repository.SubscriberLoginRepository subscriberLoginRepository;
	
	@Autowired
    private SubscriberDetailService(com.entertainment.filmland.repository.SubscriberLoginRepository subscriberLoginRepository){
    	this.subscriberLoginRepository = subscriberLoginRepository;
    }

    public Optional<Subscriber> loadUserByUsername(String email) {
         return Optional.ofNullable(subscriberLoginRepository.findByEmail(email));
    }
}
