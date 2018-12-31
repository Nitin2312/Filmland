package com.entertainment.filmland.integration;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.entertainment.filmland.FilmAppApplication;
import com.entertainment.filmland.security.TokenService;

@SpringBootTest(classes = FilmAppApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("INTEGRATION_TEST")
@ContextConfiguration
public class CucumberRoot {

    
    protected TestRestTemplate template;
    
    
    TokenService tokenService;
    
    @Autowired
    public CucumberRoot(TestRestTemplate template,TokenService tokenService){
    	this.template = template;
    	this.tokenService =tokenService;
    }
    
    

   
    public void before(String userId) {
    String token =	tokenService.createToken(userId);
  //We need to add Authorization Header for any successfull call
    	template.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
            request.getHeaders()
                    .add("Authorization",token);
            return execution.execute(request, body);
        }));
    }
    
    public void beforePost(String userId) {
        String token =	tokenService.createToken(userId);
      //We need to add Authorization Header for any successfull call
        	template.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
                request.getHeaders().add("Authorization",token);
                request.getHeaders().add("Content-Type","application/json");
                return execution.execute(request, body);
            }));
        }
    
    public void beforePostForLogin() {
      //We need to add Authorization Header for any successfull call
        	template.getRestTemplate().setInterceptors(Collections.singletonList((request, body, execution) -> {
                request.getHeaders().add("Content-Type","application/json");
                return execution.execute(request, body);
            }));
        }

}