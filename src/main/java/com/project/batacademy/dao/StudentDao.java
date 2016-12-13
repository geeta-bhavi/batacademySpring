package com.project.batacademy.dao;

import com.project.batacademy.domain.Student;

public interface StudentDao {
	
	public boolean authenticateStudent(int userId, String pwd) throws Exception;
	public Student getStudentDetails(int studentId) throws Exception;
	public int removeStudentWithId(int studentId) throws Exception;

}
