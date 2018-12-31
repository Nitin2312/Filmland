package com.entertainment.filmland.presentation.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.entertainment.filmland.delegate.SubscriberDelegate;
import com.entertainment.filmland.model.AvailableService;
import com.entertainment.filmland.model.SubscribedService;
import com.entertainment.filmland.model.SubscriberOverview;
import com.entertainment.filmland.model.SubscriptionRequest;
import com.entertainment.filmland.model.SubscriptionResponse;

@RunWith(MockitoJUnitRunner.class)
public class FilmLandControllerTest {

	@InjectMocks
	private FilmlandController filmlandController;

	@Mock
	private SubscriberDelegate subscriberDelegate;

	@Mock
	private HttpServletRequest request;

	@Mock(answer = Answers.RETURNS_DEEP_STUBS)
	private SubscriberOverview subscriberOverview;

	@Before
	public void setUp() {
		when(request.getAttribute("userId")).thenReturn("xxxxx");
	}

	

	@Test
	public void testFetchSubscriberOverView() {
		AvailableService availableService = new AvailableService();
		availableService.setAvailableContent(10);
		availableService.setName("asaa");
		availableService.setPrice(10);
		List<AvailableService> availableServiceList = new ArrayList<AvailableService>();
		availableServiceList.add(availableService);

		SubscribedService subscribedService = new SubscribedService();
		subscribedService.setRemainContent(10);
		subscribedService.setName("asaa");
		subscribedService.setPrice(10);
		List<SubscribedService> subscribedServiceList = new ArrayList<SubscribedService>();
		subscribedServiceList.add(subscribedService);

		SubscriberOverview subscriberOverview = new SubscriberOverview();
		subscriberOverview.setAvailableServices(availableServiceList);
		subscriberOverview.setSubscribedServices(subscribedServiceList);
		when(subscriberDelegate.fetchServiceOverView("xxxxx")).thenReturn(subscriberOverview);
		assertEquals(HttpStatus.OK.value(),filmlandController.fetchSubscriberOverView(request).getStatusCode().value());
	}
	
	@Test
	public void testCreateSubscription() {
		SubscriptionRequest subsciprtionRequest = new SubscriptionRequest();
		subsciprtionRequest.setSubscriptionService("NederlandseFilms");
		subsciprtionRequest.setUserId("xxxxx");
		when(subscriberDelegate.createSubscription(subsciprtionRequest))
				.thenReturn(SubscriptionResponse.builder().status(HttpStatus.CREATED).message("Sucess").build());
		assertEquals(HttpStatus.CREATED.value(),filmlandController.createSubscription(request, subsciprtionRequest).getStatusCode().value());
	}

}
