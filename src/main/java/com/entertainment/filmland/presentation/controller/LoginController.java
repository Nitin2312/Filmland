package com.entertainment.filmland.presentation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entertainment.filmland.delegate.LoginDelegate;
import com.entertainment.filmland.model.LoginCredentials;
import com.entertainment.filmland.model.LoginResponse;
import com.entertainment.filmland.model.UserDetails;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class LoginController {

	private LoginDelegate loginDelegate;

	@Autowired
	public LoginController(LoginDelegate loginDelegate) {
		this.loginDelegate = loginDelegate;
	}
	@ApiOperation(value = "This operation is being used for validating the credentials of the user "
			+ "and providing the authorization key for further operations", response=LoginResponse.class)
	@ApiResponses(value={
			@ApiResponse(code=200,message="Success"),
			@ApiResponse(code=401,message="Unauthorized"),
			@ApiResponse(code=404,message="User not found")})
	@PostMapping(path = "/login", consumes = "application/json", produces = "application/json")
	public ResponseEntity<LoginResponse> login(@RequestBody UserDetails userDetails) throws Exception {

		LoginCredentials loginCredentials = loginDelegate.isLoginCredentialsValid(userDetails);
		HttpHeaders headers = new HttpHeaders();
		headers.add("authkey", loginCredentials.getAuthKey());
		return ResponseEntity.accepted().headers(headers)
				.body(LoginResponse.builder().message("Success").userName(userDetails.getEmail()).build());

	}
	
}
