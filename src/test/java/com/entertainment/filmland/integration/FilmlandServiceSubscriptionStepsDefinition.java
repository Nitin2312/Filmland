package com.entertainment.filmland.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.entertainment.filmland.model.SubscriptionRequest;
import com.entertainment.filmland.model.SubscriptionResponse;
import com.entertainment.filmland.security.TokenService;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


@Ignore
public class FilmlandServiceSubscriptionStepsDefinition extends CucumberRoot{

	public FilmlandServiceSubscriptionStepsDefinition(TestRestTemplate template, TokenService tokenService) {
		super(template, tokenService);
	
	}

	private ResponseEntity<SubscriptionResponse> response; // output

    @When("^the client calls /subscribe with servicename$")
    public void the_client_calls_subscribe_with_servicename() throws Throwable {
    	SubscriptionRequest subscriptionRequest= new SubscriptionRequest();
    	subscriptionRequest.setSubscriptionService("Hollywood Series");
    	beforePost("nitin_2312@yahoo.co.in");
        response = template.postForEntity("/subscribe", subscriptionRequest, SubscriptionResponse.class);
    }
    
    @Then("^the client receives confirmation with status code (\\d+)$")
    public void the_client_receives_confirmation_with_status_code(int statusCode) throws Throwable {
        assertEquals(statusCode, response.getStatusCode().value());
    }
    
    
    @When("^the client calls /subscriber with subscribed servicename$")
    public void the_client_calls_subscribe_with_subscribed_servicename() throws Throwable {
    	SubscriptionRequest subscriptionRequest= new SubscriptionRequest();
    	subscriptionRequest.setSubscriptionService("Hollywood Series");
    	beforePost("nitin_2312@yahoo.co.in");
        response = template.postForEntity("/subscribe", subscriptionRequest, SubscriptionResponse.class);
    }
    
    @Then("^the client receives error User already have this service with status code (\\d+)$")
    public void the_client_receives_error_User_already_have_this_service_with_status_code(int statusCode) throws Throwable {
     assertEquals(statusCode, response.getStatusCode().value());
        assertEquals("User already have this service",response.getBody().getMessage());
    }

}
