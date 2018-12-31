package com.entertainment.filmland.filter;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.entertainment.filmland.security.TokenService;

@RunWith(MockitoJUnitRunner.class)
public class AuthorizationFilterTest {

	
	@InjectMocks
	private AuthorizationFilter authorizationFilter;
	
	@Mock
	private HttpServletRequest request;
	
	@Mock
	private HttpServletResponse response;
	
	@Mock
	private FilterChain filterChain;
	
	@Mock
	private TokenService tokenService;
	
	
	@Test
	public void testdoFilter() throws Exception{
		when(request.getRequestURI()).thenReturn("/login");
		authorizationFilter.doFilter(request, response, filterChain);
		verify(filterChain).doFilter(request, response);
	}
	
	
	@Test
	public void testUnAuthorizedFilterHandling() throws Exception{
		when(request.getRequestURI()).thenReturn("/abc");
		authorizationFilter.doFilter(request, response, filterChain);
		verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
	}
	
	@Test
	public void testSuccessEndpoint() throws Exception{
		when(request.getRequestURI()).thenReturn("/abc");
		when(request.getHeader("Authorization")).thenReturn("xxxxxxxxxxxxxxx");
		when(tokenService.isTokenValid("xxxxxxxxxxxxxxx")).thenReturn(true);
		when(tokenService.getUserIdFromToken("xxxxxxxxxxxxxxx")).thenReturn("Simba");
		authorizationFilter.doFilter(request, response, filterChain);
		verify(filterChain).doFilter(request, response);
	}
}
