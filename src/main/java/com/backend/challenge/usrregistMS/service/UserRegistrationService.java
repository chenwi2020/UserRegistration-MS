package com.backend.challenge.usrregistMS.service;

import com.backend.challenge.usrregistMS.domain.UserCredential;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserRegistrationService {
	boolean isCountryCanada = false;
	String city = "";
	@Value( "${max_uuid}" )
	private String max_uuid;
	String passwdErrMsg = "Password need to be greater than 8 characters, containing at least 1 number, 1 Capitalized letter, 1 special character in this set “_ # $ % .”";

	// create user registration
    public Map<String, String> createUserRegistration(UserCredential userCredential) {
    	System.out.println("createUserRegistration");
    	System.out.println("Received userCredential: "+userCredential.toString());
    	Map<String, String> responseMsg = new HashMap<String, String>();	
    	if ((userCredential.getUserName() == null) || ("".equals(userCredential.getUserName()))) {
    		System.out.println("User name was not provided in the request");
        	responseMsg.put("message", "userName is required");
        	responseMsg.put("uuid", Integer.toString(0));
        	return responseMsg;
    	}
    	else if ((userCredential.getPassword() == null) || ("".equals(userCredential.getPassword()))) {
    		System.out.println("Password was not provided in the request");
        	responseMsg.put("message", "password is required");
        	responseMsg.put("uuid", Integer.toString(0));
        	return responseMsg;
    	}
    	else if (!isPasswordValid(userCredential.getPassword())) {
        	responseMsg.put("message", passwdErrMsg);
        	responseMsg.put("uuid", Integer.toString(0));
        	return responseMsg;
    	}
    	else if ((userCredential.getIpAddress() == null) || ("".equals(userCredential.getIpAddress()))) {
    		System.out.println("IP address was not provided in the request");
        	responseMsg.put("message", "ipAddress is required");
        	responseMsg.put("uuid", Integer.toString(0));
        	return responseMsg;
    	}
    	else {
    		getGeoLocation(userCredential.getIpAddress());
    		if (!isCountryCanada) {
    			System.out.println("IP address is not in Canada");
    			responseMsg.put("message", "User is not eligible to register");
    			responseMsg.put("uuid", Integer.toString(0));
    			return responseMsg;
    		}
  
    		responseMsg.put("message", "Welcome " + userCredential.getUserName() + " from the city of " + city);
    		System.out.println("max_uuid: " + max_uuid);
    		//Assign a default value when triggering a JUnit test
    		if (max_uuid == null)
    			max_uuid = "150";
    		int maxUuidInt = Integer.parseInt(max_uuid);
    		Random rand = new Random();
    		int randInt = rand.nextInt(maxUuidInt);
    		randInt += 1;
    		responseMsg.put("uuid", Integer.toString(randInt));
    	}
		return responseMsg;
    }
    
    private boolean isPasswordValid(String password) {
    	boolean validPassword = false;
    	if (password.length() > 8) {
    		System.out.println("Password is > 8 chars");
    		if (password.matches(".*\\d.*")) {
    			System.out.println("There is at least one number");
    			if (password.matches(".*[A-Z].*")) {
    				System.out.println("There is at least one capitalized letter");
    				if (password.matches(".*_.*") || password.matches(".*#.*") || password.matches(".*\\$.*") || password.matches(".*%.*") || password.matches(".*\\..*")) {
    					System.out.println("There is at least on special character");
    					validPassword = true;
    				}
    			}
    			else
    				return validPassword;
    		}
    		else
    			return validPassword;
    	}
    	
    	return validPassword;
    }
    
    private void getGeoLocation(String ipAddress) {
    	String url = "http://ip-api.com/json/" + ipAddress;
    	// Toronto Canadian IP address
    	//String url = "http://ip-api.com/json/192.206.151.131";
    	// US IP address
    	//String url = "http://ip-api.com/json/9.160.32.110";
    	
    	RestTemplate restTemplate = new RestTemplate();
    	ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
    	System.out.println("ip-api response status code value: " + response.getStatusCodeValue());
    	if (response.getStatusCodeValue() == 200) {
    		System.out.println("ip-api response body: " + response.getBody());
    	}
        String resp = restTemplate.getForObject(url, String.class);
        JsonParser springParser = JsonParserFactory.getJsonParser();
        Map < String, Object > map = springParser.parseMap(resp);
        String mapArray[] = new String[map.size()];
        //System.out.println("mapArray length: " + mapArray.length);
        for (Map.Entry < String, Object > entry: map.entrySet()) {
          if ("country".equals(entry.getKey())) {
        	  System.out.println(entry.getKey() + " = " + entry.getValue());
        	  if ("Canada".equalsIgnoreCase((String) entry.getValue())) {
        		  isCountryCanada = true;
        	  }
        	  else
        		  // Invalid user registration
        		  break;
          }
          if ("city".equals(entry.getKey())) {
        	  city = (String) entry.getValue();
        	  System.out.println(entry.getKey() + " = " + entry.getValue());
          }
        }
    }
}
