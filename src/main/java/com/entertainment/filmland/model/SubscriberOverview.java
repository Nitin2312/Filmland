package com.entertainment.filmland.model;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonSerialize
@JsonInclude(Include.NON_NULL)
public class SubscriberOverview{
	private List<AvailableService> availableServices;
	private List<SubscribedService> subscribedServices;
	
}


