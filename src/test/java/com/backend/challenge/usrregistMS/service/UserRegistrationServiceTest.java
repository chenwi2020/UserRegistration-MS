package com.backend.challenge.usrregistMS.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Map;

import org.junit.jupiter.api.Test;

import com.backend.challenge.usrregistMS.domain.UserCredential;

public class UserRegistrationServiceTest {
	UserRegistrationService userRegistrationService = new UserRegistrationService();
	String canadianIpAddress = "192.206.151.131";
	String usIpAddress = "9.160.32.110";
	String passwdErrMsg = "Password need to be greater than 8 characters, containing at least 1 number, 1 Capitalized letter, 1 special character in this set “_ # $ % .”";
	
	@Test
	void testCreateUserRegistration() {
		
		UserCredential userCredential = new UserCredential("", "12345678", canadianIpAddress);
		Map<String, String> result = userRegistrationService.createUserRegistration(userCredential);
		assertEquals("userName is required", result.get("message"));
		assertEquals("0", result.get("uuid"));
		
		userCredential = new UserCredential("user1", "", canadianIpAddress);
		result = userRegistrationService.createUserRegistration(userCredential);
		assertEquals("password is required", result.get("message"));
		assertEquals("0", result.get("uuid"));
		
		userCredential = new UserCredential("user1", "12345678", null);
		result = userRegistrationService.createUserRegistration(userCredential);
		assertEquals(passwdErrMsg, result.get("message"));
		assertEquals("0", result.get("uuid"));
		
		userCredential = new UserCredential("user1", "ABCDEFGHI", null);
		result = userRegistrationService.createUserRegistration(userCredential);
		assertEquals(passwdErrMsg, result.get("message"));
		assertEquals("0", result.get("uuid"));
		
		userCredential = new UserCredential("user1", "1A3456789", null);
		result = userRegistrationService.createUserRegistration(userCredential);
		assertEquals(passwdErrMsg, result.get("message"));
		assertEquals("0", result.get("uuid"));
		
		userCredential = new UserCredential("user1", "1A345678#", null);
		result = userRegistrationService.createUserRegistration(userCredential);
		assertEquals("ipAddress is required", result.get("message"));
		assertEquals("0", result.get("uuid"));
		
		userCredential = new UserCredential("user1", "1A345678#", usIpAddress);
		result = userRegistrationService.createUserRegistration(userCredential);
		assertEquals("User is not eligible to register", result.get("message"));
		assertEquals("0", result.get("uuid"));
		
		userCredential = new UserCredential("user1", "1A345678#", canadianIpAddress);
		result = userRegistrationService.createUserRegistration(userCredential);
		assertTrue(result.get("message").matches("Welcome user1 from the city of.*"));
		
	}

}
