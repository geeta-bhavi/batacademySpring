package com.project.batacademy.domain;

public class UserAccount {

	private String name;
	private String password;

	public UserAccount() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserAccount [firstName=" + name + ", password=" + password + "]";
	}

	

}
