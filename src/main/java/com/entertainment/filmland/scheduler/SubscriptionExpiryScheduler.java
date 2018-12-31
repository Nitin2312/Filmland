package com.entertainment.filmland.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.entertainment.filmland.delegate.SubscriberDelegate;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SubscriptionExpiryScheduler {
	
	
	@Autowired
	private SubscriberDelegate subscriberDelegate;
	
	@Scheduled(fixedRate = 5000)
	public void updateSubscriptionExpiry(){
	
	
	}
	

}
