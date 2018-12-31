package com.entertainment.filmland.delegate;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.entertainment.filmland.model.FilmLandService;
import com.entertainment.filmland.model.ShareSubscriptionRequest;
import com.entertainment.filmland.model.SubscriberOverview;
import com.entertainment.filmland.model.SubscriptionRequest;
import com.entertainment.filmland.model.SubscriptionResponse;
import com.entertainment.filmland.service.SubscriberServicesDetails;

@Component
public class SubscriberDelegate {

	private SubscriberServicesDetails subscriberServicesDetails;

	@Autowired
	public SubscriberDelegate(SubscriberServicesDetails subscriberServicesDetails) {
		this.subscriberServicesDetails = subscriberServicesDetails;
	}

	/**
	 * It fetch the subcription overview of the customer.
	 * @param userId This is the user userId.
	 * @return SubscriberOverview.
	 */
	public SubscriberOverview fetchServiceOverView(String userId) {
		return subscriberServicesDetails.getSubscriberOverview(userId);
	}

	/**
	 * This method is used for creating the subscription for the existing customer
	 * @param subscriptionRequest contains the subscriptionRequest details
	 * @return subscriptionResponse result
	 */
	public SubscriptionResponse createSubscription(SubscriptionRequest subscriptionRequest) {
		Optional<FilmLandService> filmlandService = subscriberServicesDetails
				.findServiceByName(subscriptionRequest.getSubscriptionService());

		if (validateRequest(subscriptionRequest.getUserId(),filmlandService)) {
			return subscribeAndBuildResponse(subscriptionRequest, filmlandService.get());
		} else {
			return SubscriptionResponse.builder().message("User already have this service").status(HttpStatus.BAD_REQUEST).build();
		}
	}

	/**
	 * This service is used to share the subscription request for the customer
	 * @param shareSubscriptionRequest contains the subscription sharing details
	 * @return subscriptionResponse result.
	 */
	public SubscriptionResponse shareSubscription(ShareSubscriptionRequest shareSubscriptionRequest) {
		Optional<FilmLandService> filmlandService = subscriberServicesDetails
				.findServiceByName(shareSubscriptionRequest.getFriendSubscriptionRequest().getSubscriptionService());

		if (validateRequest(shareSubscriptionRequest.getFriendSubscriptionRequest().getUserId(),filmlandService)) {
			return subscribeAndBuildResponse(shareSubscriptionRequest.getFriendSubscriptionRequest(),
					filmlandService.get());
		} else {
			return SubscriptionResponse.builder().message("User already have this service").status(HttpStatus.BAD_REQUEST).build();
		}
	}

	
	
	
	
	private boolean validateRequest(String userId,Optional<FilmLandService> filmlandService) {
		return filmlandService.isPresent() && filmlandService.get().getAvailableContent() > 0
				&& !subscriberServicesDetails.findSubscriberByUserAndServiceId(userId,filmlandService.get().getServiceId())
						.isPresent();
	}

	private SubscriptionResponse subscribeAndBuildResponse(SubscriptionRequest subscriptionRequest,
			FilmLandService filmlandService) {
		return subscriberServicesDetails.createSubscription(subscriptionRequest, filmlandService)
				? SubscriptionResponse.builder().message("Successfully Subscribed").status(HttpStatus.CREATED).build()
				: SubscriptionResponse.builder().message("Something went wrong. Please try again").status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

}
