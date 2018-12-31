package com.entertainment.filmland.integration;

import static org.junit.Assert.assertNotNull;

import org.junit.Ignore;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import com.entertainment.filmland.model.SubscriberOverview;
import com.entertainment.filmland.security.TokenService;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

@Ignore
public class FilmLandServicesOverViewFeatureSteps extends CucumberRoot{

	public FilmLandServicesOverViewFeatureSteps(TestRestTemplate template, TokenService tokenService) {
		super(template, tokenService);
	}

	private ResponseEntity<SubscriberOverview> response; // output

    @When("^the client calls /services$")
    public void the_client_call_GET_Filmland_service() throws Throwable {
    	before("nitin_2312@yahoo");
        response = template.getForEntity("/services", SubscriberOverview.class);
    }
    
    @Then("^the client receives list of filmland services$")
    public void the_client_receives_list_of_filmland_services() throws Throwable {
        SubscriberOverview subscriberOverview = response.getBody();
        assertNotNull(subscriberOverview);
    }

	
}
