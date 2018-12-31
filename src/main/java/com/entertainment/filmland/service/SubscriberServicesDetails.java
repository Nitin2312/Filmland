package com.entertainment.filmland.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entertainment.filmland.model.AvailableService;
import com.entertainment.filmland.model.FilmLandService;
import com.entertainment.filmland.model.SubscribedService;
import com.entertainment.filmland.model.SubscriberOverview;
import com.entertainment.filmland.model.SubscriberService;
import com.entertainment.filmland.model.SubscriberServicePayment;
import com.entertainment.filmland.model.SubscriptionRequest;
import com.entertainment.filmland.repository.FilmlandServiceRepository;
import com.entertainment.filmland.repository.SubscriberServicePaymentRepository;
import com.entertainment.filmland.repository.SubsriberServiceRepository;

@Service
public class SubscriberServicesDetails {

	private FilmlandServiceRepository filmLandServiceRepository;
	private SubsriberServiceRepository subscriberServiceRepository;
	private SubscriberServicePaymentRepository subscriberServicePaymentRepository;

	@Autowired
	private SubscriberServicesDetails(SubsriberServiceRepository subscriberServiceRepository,
			FilmlandServiceRepository filmLandServiceRepository,
			SubscriberServicePaymentRepository subscriberServicePaymentRepository) {
		this.filmLandServiceRepository = filmLandServiceRepository;
		this.subscriberServiceRepository = subscriberServiceRepository;
		this.subscriberServicePaymentRepository = subscriberServicePaymentRepository;
	}

	/**
	 * This method is used to get the subscriber list.
	 * 
	 * @param userId
	 *            this is the userId
	 * @return
	 */
	public SubscriberOverview getSubscriberOverview(String userId) {
		Map<Long, FilmLandService> filmLandMap = new HashMap<Long, FilmLandService>();
		List<SubscriberService> subscriberOverviewList = retrieveSubscriberByUserId(userId);
		List<Long> serviceIdList = new ArrayList<Long>();
		subscriberOverviewList.forEach(subscriberService -> {
			FilmLandService filmLandService = findServiceByServiceId(subscriberService.getServiceId());
			serviceIdList.add(subscriberService.getServiceId());
			filmLandMap.put(subscriberService.getServiceId(), filmLandService);
		});

		List<FilmLandService> availableFilmLandServiceList = findServiceNotSubscribed(serviceIdList);
		return convertResponse(filmLandMap, subscriberOverviewList, availableFilmLandServiceList);
	}

	/**
	 * This is the service to retrieve the subscriber services based on the
	 * userID
	 * 
	 * @param userId
	 *            userID
	 * @return List of the subscribed services for a user
	 */
	public List<SubscriberService> retrieveSubscriberByUserId(String userId) {
		return subscriberServiceRepository.findByEmail(userId).stream()
				.filter(subscriberService -> filterActiveSubscriptions(subscriberService)).collect(Collectors.toList());
	}

	/**
	 * It checks whether the subscribed service end date is after todays date
	 * that means
	 * 
	 * @param subscriberService
	 * @return
	 */
	private boolean filterActiveSubscriptions(SubscriberService subscriberService) {
		Date date = new Date(subscriberService.getEnddate().getTime());
		return date.after(new Date());
	}

	/**
	 * It list the service offered by the FilmLand
	 * 
	 * @param serviceId
	 *            serviceID is the unique id to every Film.
	 * @return complete Film details are returned
	 */
	public FilmLandService findServiceByServiceId(long serviceId) {
		return filmLandServiceRepository.findByServiceId(serviceId);
	}

	/**
	 * It is used for calculating the number of available services for a
	 * particular customer
	 * 
	 * @param serviceIdList
	 *            this is the list of serviceID which are owned by a customer
	 * @return list of all the fimlandservice which are not subscribed.
	 */
	public List<FilmLandService> findServiceNotSubscribed(List<Long> serviceIdList) {
		return serviceIdList.isEmpty() ? filmLandServiceRepository.findAll()
				: filmLandServiceRepository.findByServiceIdNotIn(serviceIdList);
	}

	/**
	 * It is used for fetching the service by name
	 * 
	 * @param name
	 *            this is the name of the service
	 * @return it returns the optional of the FilmLandService.
	 */
	public Optional<FilmLandService> findServiceByName(String name) {
		return filmLandServiceRepository.findByName(name);
	}

	/**
	 * It is used to find the subscriber by serviceID.
	 * 
	 * @param serviceId
	 *            serviceID is the identifier of a filmland service
	 * @return it returns the optional of serviceId.
	 */
	public Optional<SubscriberService> findSubscriberByUserAndServiceId(String userId, long serviceId) {
		return subscriberServiceRepository.findByEmailAndServiceId(userId, serviceId);
	}

	/**
	 * It's a wrapper on repository to save the request for subscription
	 * 
	 * @param subscriberService
	 *            contains the subscription details.
	 * @return subscriber services
	 */
	public SubscriberService saveSubscriberService(SubscriberService subscriberService) {
		return subscriberServiceRepository.save(subscriberService);
	}

	/**
	 * It is used to update the filmLandservice once there is a new subscription
	 * for a customer.
	 * 
	 * @param filmLandService
	 *            filmlandservice updated details
	 * @return returns the updated object
	 */
	public FilmLandService updateFilmlandService(FilmLandService filmLandService) {
		return filmLandServiceRepository.save(filmLandService);
	}

	/**
	 * This method is the direct interface with payments details for the
	 * customer
	 * 
	 * @param subscriberPaymentDetails
	 *            contains the payment details like when is the next payment
	 *            due.
	 * @return subscrberservicepayment.
	 */
	public SubscriberServicePayment savePaymentDetails(SubscriberServicePayment subscriberPaymentDetails) {
		return subscriberServicePaymentRepository.save(subscriberPaymentDetails);
	}

	/**
	 * This method is used to create the subscription of the subscriber.
	 * 
	 * @param subscriptionRequest
	 *            it contains the subscriber request
	 * @return true or false based on the database updates.
	 */

	public boolean createSubscription(SubscriptionRequest subscriptionRequest, FilmLandService filmlandService) {
		SubscriberService subscriberServiceRequest = new SubscriberService();
		subscriberServiceRequest.setEmail(subscriptionRequest.getUserId());
		subscriberServiceRequest.setServiceId(filmlandService.getServiceId());
		subscriberServiceRequest.setStartDate(new Timestamp(System.currentTimeMillis()));
		subscriberServiceRequest.setEnddate(new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000));
		SubscriberService subscriberService = saveSubscriberService(subscriberServiceRequest);
		filmlandService.setAvailableContent(filmlandService.getAvailableContent() - 1);
		FilmLandService filmLandService = updateFilmlandService(filmlandService);
		SubscriberServicePayment subscriberServicePayment = savePaymentDetails(
				createRequestForPaymentDetails(subscriberServiceRequest));
		return subscriberService != null && filmLandService != null && subscriberServicePayment != null;
	}

	private SubscriberServicePayment createRequestForPaymentDetails(SubscriberService subscriberServiceRequest) {
		return SubscriberServicePayment.builder()
				.subscriberServiceIdentifier(subscriberServiceRequest.getSubscriberServiceIdentifier())
				.paymentdate(subscriberServiceRequest.getEnddate()).build();
	}

	private SubscriberOverview convertResponse(Map<Long, FilmLandService> filmLandMap,
			List<SubscriberService> subscriberOverviewList, List<FilmLandService> availableFilmLandServiceList) {

		return subscriberOverviewList.isEmpty()
				? SubscriberOverview.builder()
						.availableServices(availableFilmLandServiceList.stream()
								.map(filmLandService -> formResponse(filmLandService)).collect(Collectors.toList()))
						.build()
				: SubscriberOverview.builder().subscribedServices(subscriberOverviewList.stream()
						.map(subscriberService -> formResponse(subscriberService,
								filmLandMap.get(subscriberService.getServiceId())))
						.collect(Collectors.toList()))
						.availableServices(availableFilmLandServiceList.stream()
								.map(filmLandService -> formResponse(filmLandService)).collect(Collectors.toList()))
						.build();

	}

	private SubscribedService formResponse(SubscriberService subscriberService, FilmLandService filmLandService) {
		return new SubscribedService(filmLandService.getName(), filmLandService.getAvailableContent(),
				filmLandService.getPrice(), subscriberService.getStartDate());
	}

	private AvailableService formResponse(FilmLandService filmLandService) {
		AvailableService availableService = new AvailableService();
		availableService.setAvailableContent(filmLandService.getAvailableContent());
		availableService.setPrice(filmLandService.getPrice());
		availableService.setName(filmLandService.getName());
		return availableService;
	}
}
