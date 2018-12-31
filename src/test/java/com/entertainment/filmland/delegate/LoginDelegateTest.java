package com.entertainment.filmland.delegate;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import com.entertainment.filmland.exception.SubscriberNotFoundException;
import com.entertainment.filmland.model.LoginCredentials;
import com.entertainment.filmland.model.Subscriber;
import com.entertainment.filmland.model.UserDetails;
import com.entertainment.filmland.security.GenerateHashHandler;
import com.entertainment.filmland.security.TokenService;
import com.entertainment.filmland.service.SubscriberDetailService;

@RunWith(MockitoJUnitRunner.class)
public class LoginDelegateTest {

	@InjectMocks
	private LoginDelegate loginDelegate;

	@Mock
	private SubscriberDetailService subscriberDetailService;

	@Mock
	private TokenService tokenService;

	@Spy
	private GenerateHashHandler generateHashHandler;

	@Test
	public void successLogin() throws Exception {
		UserDetails userDetails = new UserDetails();
		userDetails.setEmail("abc_312@yahoo");
		userDetails.setPassword("abcs123");
		;
		Subscriber subscriber = new Subscriber();
		subscriber.setEmail("abc_312@yahoo");
		subscriber.setPassword("C747238F43C8D25F867906C2441A3CACD948D9D2");
		when(subscriberDetailService.loadUserByUsername("abc_312@yahoo")).thenReturn(Optional.ofNullable(subscriber));
		when(tokenService.createToken("abc_312@yahoo")).thenReturn("sssssssxxxxasdasdasd");
		LoginCredentials response = loginDelegate.isLoginCredentialsValid(userDetails);
		assertTrue(response.isValid());
	}

	@Test(expected = SubscriberNotFoundException.class)
	public void failedLogin() throws Exception {
		UserDetails userDetails = new UserDetails();
		userDetails.setEmail("abc_312");
		userDetails.setPassword("abddcioa12");
		when(subscriberDetailService.loadUserByUsername("abc_312")).thenReturn(Optional.ofNullable(null));
		loginDelegate.isLoginCredentialsValid(userDetails);

	}

}
