package com.project.batacademy.services;

public interface AuthenticateService {
	
	public String checkIfStudentExists(int userId, String password);
	public String checkIfFacultyExists(int userId, String password);
	
}
