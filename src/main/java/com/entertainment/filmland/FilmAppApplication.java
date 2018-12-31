package com.entertainment.filmland;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.entertainment.filmland.delegate.LoginDelegate;
import com.entertainment.filmland.delegate.SubscriberDelegate;
import com.entertainment.filmland.filter.AuthorizationFilter;
import com.entertainment.filmland.presentation.controller.FilmlandController;
import com.entertainment.filmland.presentation.controller.LoginController;
import com.entertainment.filmland.repository.SubscriberLoginRepository;
import com.entertainment.filmland.scheduler.SubscriptionExpiryScheduler;
import com.entertainment.filmland.security.GenerateHashHandler;
import com.entertainment.filmland.security.TokenService;
import com.entertainment.filmland.service.SubscriberDetailService;
import com.entertainment.filmland.service.SubscriberServicesDetails;
@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackageClasses = { LoginController.class,FilmlandController.class, SubscriberDetailService.class,
		SubscriberLoginRepository.class, LoginDelegate.class, SubscriberServicesDetails.class, SubscriberDelegate.class,
		SubscriptionExpiryScheduler.class, TokenService.class, AuthorizationFilter.class,GenerateHashHandler.class })
public class FilmAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmAppApplication.class, args);
	}
	

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}

		};
	}
}
