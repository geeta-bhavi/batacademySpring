package com.project.batacademy.dao;

import com.project.batacademy.domain.Student;

public interface StudentDao {
	
	public Student getStudentDetails(int userId, String pwd);
	public Student getStudentDetails(int studentId);

}
