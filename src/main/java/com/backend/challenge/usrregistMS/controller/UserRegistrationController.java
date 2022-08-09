package com.backend.challenge.usrregistMS.controller;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.challenge.usrregistMS.domain.UserCredential;
import com.backend.challenge.usrregistMS.service.UserRegistrationService;

@RestController
public class UserRegistrationController {

	private UserRegistrationService userRegistrationService;
	
    @Autowired
    public UserRegistrationController (UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

	@PostMapping("/createUserRegistration")
	public ResponseEntity<Map<String, String>> createUserRegistration(@Valid @RequestBody UserCredential userCredential) {
	    	Map<String, String> result = userRegistrationService.createUserRegistration(userCredential);
	    	if ("0".equals(result.get("uuid")))
	    		return new ResponseEntity<Map<String, String>> (result, HttpStatus.BAD_REQUEST);
	    	else
	    		return new ResponseEntity<Map<String, String>> (result, HttpStatus.OK);
	}
	
}
