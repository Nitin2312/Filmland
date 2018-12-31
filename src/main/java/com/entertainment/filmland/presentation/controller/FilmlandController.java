package com.entertainment.filmland.presentation.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.entertainment.filmland.delegate.SubscriberDelegate;
import com.entertainment.filmland.model.LoginResponse;
import com.entertainment.filmland.model.ShareSubscriptionRequest;
import com.entertainment.filmland.model.SubscriberOverview;
import com.entertainment.filmland.model.SubscriptionRequest;
import com.entertainment.filmland.model.SubscriptionResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class FilmlandController {

	private final SubscriberDelegate subscriberDelegate;

	@Autowired
	public FilmlandController(SubscriberDelegate subscriberDelegate) {
		this.subscriberDelegate = subscriberDelegate;
	}
	@ApiOperation(value = "This operation provides the complete overview of the subscriber", response=SubscriberOverview.class)
	@ApiResponses(value={
			@ApiResponse(code=200,message="Success"),
			@ApiResponse(code=401,message="Unauthorized"	)})
	@GetMapping(path = "/services")
	public ResponseEntity<SubscriberOverview> fetchSubscriberOverView(HttpServletRequest request) {
		return ResponseEntity.ok().body(subscriberDelegate.fetchServiceOverView(extractUserId(request)));
	}
	
	@ApiOperation(value = "This operation is used to subscribe the customer for new service in fimland", response=SubscriptionResponse.class)
	@ApiResponses(value={
			@ApiResponse(code=201,message="Successfully Subscribed"),
			@ApiResponse(code=401,message="Unauthorized"),
			@ApiResponse(code=400,message="User already have this service"),
			@ApiResponse(code=500,message="Something went wrong. Please try again")})
	@PostMapping(path = "/subscribe", consumes = "application/json", produces = "application/json")
	public ResponseEntity<SubscriptionResponse> createSubscription(HttpServletRequest request,
			@RequestBody SubscriptionRequest subscriptionRequest) {
		subscriptionRequest.setUserId(extractUserId(request));
		SubscriptionResponse subscriptionResponse = subscriberDelegate.createSubscription(subscriptionRequest);

		return subscriptionResponse.getStatus().compareTo(HttpStatus.CREATED) == 0
				? ResponseEntity.status(HttpStatus.CREATED).body(subscriptionResponse)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(subscriptionResponse);
	}
	@ApiOperation(value = "This operation is used to share the filmland service with a friend and subscribe it on his behalf", response=SubscriptionResponse.class)
	@ApiResponses(value={
			@ApiResponse(code=201,message="Successfully Subscribed"),
			@ApiResponse(code=401,message="Unauthorized"),
			@ApiResponse(code=400,message="User already have this service"),
			@ApiResponse(code=500,message="Something went wrong. Please try again")})
	@PostMapping(path = "/shareservice", consumes = "application/json", produces = "application/json")
	public ResponseEntity<SubscriptionResponse> shareSubscription(HttpServletRequest request,
			@RequestBody ShareSubscriptionRequest shareSubscriptionRequest) {
		shareSubscriptionRequest.setUserId(extractUserId(request));
		SubscriptionResponse subscriptionResponse = subscriberDelegate.shareSubscription(shareSubscriptionRequest);
		return subscriptionResponse.getStatus().compareTo(HttpStatus.CREATED) == 0
				? ResponseEntity.status(HttpStatus.CREATED).body(subscriptionResponse)
				: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(subscriptionResponse);
	}

	private String extractUserId(HttpServletRequest request) {
		return (String) request.getAttribute("userId");
	}
}
