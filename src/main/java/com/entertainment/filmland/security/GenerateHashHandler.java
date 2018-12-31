package com.entertainment.filmland.security;

import java.lang.reflect.Field;
import java.security.MessageDigest;

import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Component;


@Component
public class GenerateHashHandler {
	/**
	 * This method generates the hash for the password and update the request with hashed password
	 * @param ob contains the request object 
	 * @throws Exception
	 */
	public void handle(Object ob) throws Exception {
		Field[] fields = ob.getClass().getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(GenerateHash.class)) {
				GenerateHash annotatedField = field.getAnnotation(GenerateHash.class);
				field.setAccessible(true); 
				String algorithmName = annotatedField.algorithm();
				String password = field.get(ob).toString();
				MessageDigest md = MessageDigest.getInstance(algorithmName);
				md.update(password.getBytes());
				byte[] digest = md.digest();
				String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
				field.set(ob, myHash);
			}
		}
	}
}
