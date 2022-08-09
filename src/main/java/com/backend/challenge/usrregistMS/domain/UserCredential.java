package com.backend.challenge.usrregistMS.domain;


public class UserCredential {
	private String userName;
	private String password;
	private String ipAddress;
	
	public UserCredential(String userName, String password, String ipAddress) {
		super();
		this.userName = userName;
		this.password = password;
		this.ipAddress = ipAddress;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Override
	public String toString() {
		return "UserCredential [userName=" + userName + ", password=" + password + ", ipAddress=" + ipAddress + "]";
	}


}
