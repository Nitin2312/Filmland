package com.entertainment.filmland.delegate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.entertainment.filmland.exception.SubscriberNotFoundException;
import com.entertainment.filmland.model.LoginCredentials;
import com.entertainment.filmland.model.Subscriber;
import com.entertainment.filmland.model.UserDetails;
import com.entertainment.filmland.security.GenerateHashHandler;
import com.entertainment.filmland.security.TokenService;
import com.entertainment.filmland.service.SubscriberDetailService;

@Component
public class LoginDelegate {

	private final SubscriberDetailService subscriberDetailService;
	private TokenService tokenService;
	private GenerateHashHandler generateHashHandler;

	@Autowired
	public LoginDelegate(com.entertainment.filmland.service.SubscriberDetailService subscriberDetailService,
			TokenService tokenService, GenerateHashHandler generateHashHandler) {
		this.subscriberDetailService = subscriberDetailService;
		this.tokenService = tokenService;
		this.generateHashHandler = generateHashHandler;
	}

	public LoginCredentials isLoginCredentialsValid(UserDetails userDetails) throws Exception {
	
		Optional<Subscriber> subscriber = subscriberDetailService.loadUserByUsername(userDetails.getEmail());
		generateHashHandler.handle(userDetails);
		if (subscriber.isPresent() && validateCredentials(subscriber.get(), userDetails))
			return LoginCredentials.builder().isValid(true)
					.authKey(tokenService.createToken(subscriber.get().getEmail())).build();
		else
			throw new SubscriberNotFoundException("User Not found");
	}

	private boolean validateCredentials(Subscriber subscriber, UserDetails userDetails) {
		return subscriber.getPassword().equalsIgnoreCase(userDetails.getPassword());
	}
}
