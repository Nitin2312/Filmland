package com.entertainment.filmland.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.micrometer.core.lang.NonNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize
@JsonIgnoreProperties(ignoreUnknown=true)
public class ShareSubscriptionRequest {

	private String userId;
	@NonNull
	private SubscriptionRequest friendSubscriptionRequest;
}
