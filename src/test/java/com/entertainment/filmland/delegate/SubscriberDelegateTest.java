package com.entertainment.filmland.delegate;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.entertainment.filmland.model.FilmLandService;
import com.entertainment.filmland.model.ShareSubscriptionRequest;
import com.entertainment.filmland.model.SubscriberService;
import com.entertainment.filmland.model.SubscriptionRequest;
import com.entertainment.filmland.service.SubscriberServicesDetails;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberDelegateTest {

	@InjectMocks
	private SubscriberDelegate subscriberDelegate;

	@Mock
	private SubscriberService subscriberService;

	@Mock
	private SubscriberServicesDetails subscriberServiceDetail;

	@Mock
	private FilmLandService filmLandService;

	@Test
	public void testSuccessSubscription() {

		SubscriptionRequest request = new SubscriptionRequest();
		request.setUserId("abc_2333");
		request.setSubscriptionService("NederlandseFilms");
		when(subscriberServiceDetail.findServiceByName(request.getSubscriptionService()))
				.thenReturn(Optional.ofNullable(filmLandService));
		when(subscriberServiceDetail.createSubscription(request, filmLandService)).thenReturn(true);
		when(filmLandService.getAvailableContent()).thenReturn(Long.valueOf("1"));
		when(filmLandService.getServiceId()).thenReturn(Long.valueOf("1"));
		when(subscriberServiceDetail.findSubscriberByUserAndServiceId("abc_2333",filmLandService.getServiceId()))
				.thenReturn(Optional.ofNullable(null));
		assertEquals(subscriberDelegate.createSubscription(request).getStatus(), HttpStatus.CREATED);
	}

	@Test
	public void testFailSubscription() {

		SubscriptionRequest request = new SubscriptionRequest();
		request.setUserId("abc_2333");
		request.setSubscriptionService("NederlandseFilms");
		when(subscriberServiceDetail.findServiceByName(request.getSubscriptionService()))
				.thenReturn(Optional.ofNullable(filmLandService));
		when(filmLandService.getAvailableContent()).thenReturn(Long.valueOf("1"));
		when(filmLandService.getServiceId()).thenReturn(Long.valueOf("1"));
		when(subscriberServiceDetail.findSubscriberByUserAndServiceId("abc_2333",filmLandService.getServiceId()))
				.thenReturn(Optional.ofNullable(new SubscriberService()));
		assertEquals(subscriberDelegate.createSubscription(request).getStatus(), HttpStatus.BAD_REQUEST);
	}

	@Test
	public void testShareSuccessSubscription() {

		SubscriptionRequest request = new SubscriptionRequest();
		request.setUserId("abc_2333");
		request.setSubscriptionService("NederlandseFilms");

		ShareSubscriptionRequest shareSubscriptionRequest = new ShareSubscriptionRequest();
		shareSubscriptionRequest.setFriendSubscriptionRequest(request);
		shareSubscriptionRequest.setUserId("abcs123123");

		when(subscriberServiceDetail
				.findServiceByName(shareSubscriptionRequest.getFriendSubscriptionRequest().getSubscriptionService()))
						.thenReturn(Optional.ofNullable(filmLandService));
		when(filmLandService.getAvailableContent()).thenReturn(Long.valueOf("1"));
		when(subscriberServiceDetail.createSubscription(request, filmLandService)).thenReturn(true);
		when(filmLandService.getServiceId()).thenReturn(Long.valueOf("1"));
		when(subscriberServiceDetail.findSubscriberByUserAndServiceId("abc_2333",filmLandService.getServiceId()))
				.thenReturn(Optional.ofNullable(null));

		assertEquals(subscriberDelegate.shareSubscription(shareSubscriptionRequest).getStatus(), HttpStatus.CREATED);

	}
	
	
	
	@Test
	public void testShareSubscriptionFailure() {

		SubscriptionRequest request = new SubscriptionRequest();
		request.setUserId("abc_2333");
		request.setSubscriptionService("NederlandseFilms");

		ShareSubscriptionRequest shareSubscriptionRequest = new ShareSubscriptionRequest();
		shareSubscriptionRequest.setFriendSubscriptionRequest(request);
		shareSubscriptionRequest.setUserId("abcs123123");

		when(subscriberServiceDetail
				.findServiceByName(shareSubscriptionRequest.getFriendSubscriptionRequest().getSubscriptionService()))
						.thenReturn(Optional.ofNullable(filmLandService));
		when(filmLandService.getAvailableContent()).thenReturn(Long.valueOf("1"));
		when(filmLandService.getServiceId()).thenReturn(Long.valueOf("1"));
		when(subscriberServiceDetail.findSubscriberByUserAndServiceId("abc_2333",filmLandService.getServiceId()))
				.thenReturn(Optional.ofNullable(new SubscriberService()));

		assertEquals(subscriberDelegate.shareSubscription(shareSubscriptionRequest).getStatus(), HttpStatus.BAD_REQUEST);

	}

}
