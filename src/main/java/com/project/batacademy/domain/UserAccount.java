package com.project.batacademy.domain;

public class UserAccount {

	private int id;
	private String password;

	public UserAccount() {
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean hasPassword(String testPswd) {
		return testPswd.equals(password);
	}

	public String toString() {
		return "Account[ User: " + id + "  password: " + password + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
