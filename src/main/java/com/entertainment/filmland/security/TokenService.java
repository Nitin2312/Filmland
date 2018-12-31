package com.entertainment.filmland.security;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TokenService {

	public static final String TOKEN_SECRET = "s4T2zOIWHMM1sxq";

	/**
	 * This method is used to generate the token for every new customer.
	 * 
	 * @param userId
	 *            this is the key which is used for encryption
	 * @return a string with the token
	 */
	public String createToken(String userId) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			String token = JWT.create().withClaim("userId", userId.toString()).withClaim("createdAt", new Date())
					.withIssuedAt(new Date()).withExpiresAt(generateExpirationTime()).sign(algorithm);
			return token;
		} catch (UnsupportedEncodingException exception) {
			log.error("Unsupported encoding exception");
			log.debug("Exception occured", exception);
		} catch (JWTCreationException exception) {
			log.error("Token Creation exception");
			log.debug("Exception occured", exception);
		}
		return null;
	}

	private Date generateExpirationTime() {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, 1);
		Date expirationDate = c.getTime();
		return expirationDate;
	}

	/**
	 * This is the token verification method which takes JWT as input and
	 * verifies and provides the actual string
	 * 
	 * @param token
	 *            this is encrypted token
	 * @return the userid.
	 */
	public String getUserIdFromToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
			JWTVerifier verifier = JWT.require(algorithm).build();
			DecodedJWT jwt = verifier.verify(token);
			return jwt.getClaim("userId").asString();
		} catch (UnsupportedEncodingException exception) {
			log.error("UnsupportedEncoding exception");
			log.debug("Exception occured", exception);
			return null;
		} catch (JWTVerificationException exception) {
			log.error("Token Verification exception");
			log.debug("Exception occured", exception);
			return null;
		}
		}

	/**
	 * It redirects to getUserIdFromToken
	 * 
	 * @param token
	 *            jwt token
	 * @return true or false if the token is valid.
	 */
	public boolean isTokenValid(String token) {
		String userId = this.getUserIdFromToken(token);
		return userId != null;
	}
}