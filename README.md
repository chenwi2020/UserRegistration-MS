## UserRegistration-MS
User registration simulation MS

### Message response values
A successful response message would be similar to "Welcome user1 from the city of Concord"

The following error messages could be displayed:
* Password needs to be greater than 8 characters, containing at least 1 number, 1 Capitalized letter, 1 special character in this set “_ # $ % .”
* userName is required
* password is required
* ipAddress is required
* User is not eligible to register NOTE: Need to use Canadian IP address e.g. 192.206.151.131
* Unable to get geolocation data. Please try later.

### UUID response values
* The uuid value is "0" for any error response
* The uuid value is a random value for successful registration ranging from "1" to a max value specified in the application.properties file.
However, the max value is set to "150" if API is triggered from JUnit test.
