package com.entertainment.filmland.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.entertainment.filmland.model.LoginResponse;
import com.entertainment.filmland.model.UserDetails;
import com.entertainment.filmland.security.TokenService;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Ignore
public class FilmLandSubscriberLoginStepDefinitions extends CucumberRoot {

	@Rule
	ExpectedException ex = ExpectedException.none();

	@Autowired
	public FilmLandSubscriberLoginStepDefinitions(TestRestTemplate template, TokenService tokenService) {
		super(template, tokenService);

	}

	private ResponseEntity<LoginResponse> response; // output
	private UserDetails userDetailsCorrect = null;
	
	@Before
	public void setUp(){
		beforePostForLogin();
	}
	
	@Given("^the credentials are known$")
	public void the_credentials_are_known() throws Throwable{
		
		
		userDetailsCorrect = new UserDetails();
		userDetailsCorrect.setEmail("nitin_2312@yahoo.co.in");
		userDetailsCorrect.setPassword("abcs123");
	}
	
	@When("^the client call the /login with the credentials$")
	public void the_client_calls_login_with_credentials() throws Throwable {
		response = template.postForEntity("/login", userDetailsCorrect, LoginResponse.class);
	}

	@Then("^the client should receive a authkey$")
	public void the_client_should_receive_a_authkey() throws Throwable {
		assertNotNull(response.getHeaders().get("authkey"));

	}
	/*
	@Given("^the credentials should be wrong$")
	public void the_credentials_should_be_wrong() throws Throwable{
		
		incorrectUserDetails = new UserDetails();
		incorrectUserDetails.setEmail("nitin_23112@yahoo.co.in");
		incorrectUserDetails.setPassword("abcs123");
	}
	
	@When("^the client calls /login with wrong username$")
	public void the_client_calls_login_with_wrong_username() throws Throwable {
		try{
		 template.postForEntity("/login", incorrectUserDetails,Object.class);
		}catch(HttpStatusCodeException httpStatusCodeExcption){
			httpStatusCode = httpStatusCodeExcption.getRawStatusCode();
		}
	}

	@Then("^the client receives error user not found with status code$")
	public void the_client_receives_error_user_not_found_with_status_code() throws Throwable {
		assertEquals(404, httpStatusCode);
	}*/
}
