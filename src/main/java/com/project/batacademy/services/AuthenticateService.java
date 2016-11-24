package com.project.batacademy.services;

public interface AuthenticateService {
	
	public boolean checkIfStudentExists(int userId, String password);
	public boolean checkIfFacultyExists(int userId, String password);
	
}
