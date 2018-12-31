package com.entertainment.filmland.integration;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources",plugin={"pretty", "html:target/cucumber"})
public class FilmLandServicesCucumberIntegrationIT {

	
}