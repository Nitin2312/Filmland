package com.entertainment.filmland.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.entertainment.filmland.model.ShareSubscriptionRequest;
import com.entertainment.filmland.model.SubscriptionRequest;
import com.entertainment.filmland.model.SubscriptionResponse;
import com.entertainment.filmland.security.TokenService;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


@Ignore
public class FilmlandServiceShareSubscriptionStepsDefinition extends CucumberRoot{

	public FilmlandServiceShareSubscriptionStepsDefinition(TestRestTemplate template, TokenService tokenService) {
		super(template, tokenService);
	
	}

	private ResponseEntity<SubscriptionResponse> response; // output

    @When("^the client calls /shareservice with servicename and userId of the friend$")
    public void the_client_calls_shareservice_with_servicename() throws Throwable {
    	ShareSubscriptionRequest shareSubscriptionRequest= new ShareSubscriptionRequest();
    	shareSubscriptionRequest.setUserId("nitin_2312@yahoo.co.in");
    	SubscriptionRequest subscriptionRequest= new SubscriptionRequest();
    	subscriptionRequest.setUserId("nitin_23121@yahoo.co.in");
    	subscriptionRequest.setSubscriptionService("Hollywood Series");
    	shareSubscriptionRequest.setFriendSubscriptionRequest(subscriptionRequest);
    	beforePost("nitin_2312@yahoo.co.in");
        response = template.postForEntity("/shareservice", shareSubscriptionRequest, SubscriptionResponse.class);
    }
    
    @Then("^the client receives confirmation with status code (\\d+) that friend is also subscribed for the service$")
    public void the_client_receives_confirmation_with_status_code(int statusCode) throws Throwable {
        assertEquals(statusCode, response.getStatusCode().value());
    }
    
    
    
    @When("^the client calls /shareservice with servicename and userId of the friend with same filmland service$")
    public void the_client_calls_shareservice_for_friend_with_servicename() throws Throwable {
    	ShareSubscriptionRequest shareSubscriptionRequest= new ShareSubscriptionRequest();
    	shareSubscriptionRequest.setUserId("nitin_2312@yahoo.co.in");
    	SubscriptionRequest subscriptionRequest= new SubscriptionRequest();
    	subscriptionRequest.setUserId("nitin_23121@yahoo.co.in");
    	subscriptionRequest.setSubscriptionService("Hollywood Series");
    	shareSubscriptionRequest.setFriendSubscriptionRequest(subscriptionRequest);
    	beforePost("nitin_2312@yahoo.co.in");
        response = template.postForEntity("/shareservice", shareSubscriptionRequest, SubscriptionResponse.class);
    }
    
    @Then("^the client receives error as bad request with status code (\\d+) 	that friend is already subscribed for the service$")
    public void the_client_receives_error_as_bad_request_with_status_code_that_friend_is_already_subscribed_for_the_service(int statusCode) throws Exception {
    	 assertEquals(statusCode, response.getStatusCode().value());
    }
}
