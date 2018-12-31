package com.entertainment.filmland.security;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TokenServiceTest {

	@InjectMocks
	private TokenService tokenService;

	private String sampleToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjcmVhdGVkQXQiOjE1NDYwMTk1ODgsImV4cCI6MTU0NjAxOTY0OCwidXNlcklkIjoibml0aW5fMjMxMkB5YWhvby5jby5pbiIsImlhdCI6MTU0NjAxOTU4OH0.vIKy61fBpnloOugbQK0bOX-ELiec5JdPGTeleCUKYIw";
	
	@Test
	public void testCreateTokenService() {
		assertNotNull(tokenService.createToken("abcs123"));
	}
	
	@Test
	public void testIsTokenValid(){
		assertFalse(tokenService.isTokenValid(sampleToken));
	}
}
