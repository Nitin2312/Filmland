package com.entertainment.filmland.presentation.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.entertainment.filmland.delegate.LoginDelegate;
import com.entertainment.filmland.model.LoginCredentials;
import com.entertainment.filmland.model.UserDetails;

@RunWith(MockitoJUnitRunner.class)
public class LoginControllerTest {
	
	@InjectMocks
	private LoginController loginController;

	@Mock
	private LoginDelegate loginDelegate;

	@Mock
	private HttpServletRequest request;
	
	
	@Test
	public void testLogin() throws Exception {
		UserDetails userDetails = new UserDetails();
		userDetails.setEmail("abc@ys.com");
		userDetails.setPassword("xxcesdad");
		when(loginDelegate.isLoginCredentialsValid(userDetails)).thenReturn(LoginCredentials.builder().authKey("sdfsgdfjdiolkdfgioojiasjdasd").isValid(true).build());
		assertEquals(HttpStatus.ACCEPTED.value(), loginController.login(userDetails).getStatusCode().value());
	}
}
