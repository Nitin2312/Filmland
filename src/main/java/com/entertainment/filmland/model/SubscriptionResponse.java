package com.entertainment.filmland.model;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonSerialize
@AllArgsConstructor
@NoArgsConstructor
@JsonDeserialize
@Builder
@JsonInclude(Include.NON_NULL)
public class SubscriptionResponse {
	private String message;
	private HttpStatus status;
}
