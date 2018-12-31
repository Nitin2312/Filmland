package com.entertainment.filmland.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.GenericFilterBean;

import com.entertainment.filmland.security.TokenService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class AuthorizationFilter extends GenericFilterBean {

	private TokenService tokenService;

	@Autowired
	AuthorizationFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String token = request.getHeader("Authorization");
		log.info(token);
		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.sendError(HttpServletResponse.SC_OK, "success");
			return;
		}

		if (allowRequestWithoutToken(request)) {
			response.setStatus(HttpServletResponse.SC_OK);
			filterChain.doFilter(req, res);
		} else {
			if (token == null || !tokenService.isTokenValid(token)) {
				System.out.println("Token is here"+token);
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				String userId = new String(tokenService.getUserIdFromToken(token));
				log.info(userId);
				request.setAttribute("userId", userId);
				filterChain.doFilter(req, res);

			}
		}

	}

	private boolean allowRequestWithoutToken(HttpServletRequest request) {
		System.out.println(request.getRequestURI());
		if (request.getRequestURI().contains("/login") || request.getRequestURI().contains("/h2-console")
				||request.getRequestURI().contains("/v2/api-docs")
				||request.getRequestURI().contains("/api-docs")) {
			return true;
		}
		return false;
	}
}
