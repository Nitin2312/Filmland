package com.entertainment.filmland.service;

import static org.hamcrest.CoreMatchers.any;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.entertainment.filmland.model.FilmLandService;
import com.entertainment.filmland.model.SubscriberOverview;
import com.entertainment.filmland.model.SubscriberService;
import com.entertainment.filmland.model.SubscriberServicePayment;
import com.entertainment.filmland.model.SubscriptionRequest;
import com.entertainment.filmland.repository.FilmlandServiceRepository;
import com.entertainment.filmland.repository.SubscriberServicePaymentRepository;
import com.entertainment.filmland.repository.SubsriberServiceRepository;

@RunWith(MockitoJUnitRunner.class)
public class SubscriberServiceDetailsTest {

	@InjectMocks
	private SubscriberServicesDetails subscriberServicesDetails;

	@Mock
	private SubsriberServiceRepository subscriberServiceRepository;

	@Mock
	private FilmlandServiceRepository filmLandServiceRepository;
	
	@Mock
	private SubscriberServicePaymentRepository subscriberServicePaymentRepository;
	
	@Mock
	private SubscriberService subscriberService;
	
	@Mock
	private FilmLandService filmLandService;
	
	@Mock
	private SubscriberServicePayment subscriberServicePayment;

	@Test
	public void testSubscriptionOverView(){
		
		String userId = "acvwer123";
		List<Long>serviceIdList = new ArrayList<Long>();
		serviceIdList.add((long) 1);
		
		when(subscriberServiceRepository.findByEmail(userId)).thenReturn(createResponseSubscriberServiceOverviewList());
		when(filmLandServiceRepository.findByServiceId(Long.valueOf(1))).thenReturn(createFilmLandResponse());
		when(filmLandServiceRepository.findByServiceIdNotIn(serviceIdList)).thenReturn(availableServicesFromFilmandResponse());
		SubscriberOverview subscriberOverView=subscriberServicesDetails.getSubscriberOverview(userId);
		assertEquals(subscriberOverView.getAvailableServices().get(0).getAvailableContent(), 10);
		assertEquals(subscriberOverView.getAvailableServices().get(0).getName(), "English");
		assertEquals(subscriberOverView.getAvailableServices().get(0).getPrice(), 11);
		assertEquals(subscriberOverView.getSubscribedServices().get(0).getName(), "Nederlandse");
		assertEquals(subscriberOverView.getSubscribedServices().get(0).getRemainContent(), 10);
		assertEquals(subscriberOverView.getSubscribedServices().get(0).getPrice(), 11);

		
	}
	
	@Test
	public void testSubscriptionOverViewWithNoSubscriptionForCustomer(){
		
		String userId = "acvwer123";
		List<Long>serviceIdList = new ArrayList<Long>();
		serviceIdList.add((long) 1);
		
		when(subscriberServiceRepository.findByEmail(userId)).thenReturn(new ArrayList<SubscriberService>());
		when(filmLandServiceRepository.findAll()).thenReturn(availableServicesFromFilmandResponse());
		SubscriberOverview subscriberOverView=subscriberServicesDetails.getSubscriberOverview(userId);
		assertEquals(subscriberOverView.getAvailableServices().get(0).getAvailableContent(), 10);
		assertEquals(subscriberOverView.getAvailableServices().get(0).getName(), "English");
		assertEquals(subscriberOverView.getAvailableServices().get(0).getPrice(), 11);
		assertNull(subscriberOverView.getSubscribedServices());
		
	}
	
	
	

	private List<SubscriberService> createResponseSubscriberServiceOverviewList() {
		List<SubscriberService> subscriberServiceList = new ArrayList<SubscriberService>();
		SubscriberService subscriberService = new SubscriberService();
		subscriberService.setEmail("aaacc");
		subscriberService.setEnddate(new Timestamp(System.currentTimeMillis() + 30 * 60 * 1000));
		subscriberService.setServiceId(1);
		subscriberService.setSubscriberServiceIdentifier(1);
		subscriberService.setStartDate(new Timestamp(System.currentTimeMillis()));
		subscriberServiceList.add(subscriberService);
		return subscriberServiceList;
	}

	private FilmLandService createFilmLandResponse() {
		FilmLandService filmLandService = new FilmLandService();
		filmLandService.setAvailableContent(10);
		filmLandService.setName("Nederlandse");
		filmLandService.setServiceId(1);
		filmLandService.setPrice(11);
		return filmLandService;

	}

	private List<FilmLandService> availableServicesFromFilmandResponse() {
		List<FilmLandService> filmLandServiceList = new ArrayList<FilmLandService>();
		FilmLandService filmLandService = new FilmLandService();
		filmLandService.setAvailableContent(10);
		filmLandService.setName("English");
		filmLandService.setServiceId(2);
		filmLandService.setPrice(11);
		filmLandServiceList.add(filmLandService);
		return filmLandServiceList;

	}

}
