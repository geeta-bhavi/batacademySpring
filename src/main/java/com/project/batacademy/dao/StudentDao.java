package com.project.batacademy.dao;

import com.project.batacademy.domain.Student;

public interface StudentDao {
	
	public boolean authenticateStudent(int userId, String pwd) throws Exception;
	public Student getStudentDetails(int studentId) throws Exception;
	public Student getStudentWithPassword(int studentId) throws Exception;
	public int removeStudentWithId(int studentId) throws Exception;
	public float getStudentCGPA(int studentId) throws Exception;
	public void updateStudentCGPA(int studentId, float cgpa) throws Exception;
	public void updateRegisteredColumn(boolean registered) throws Exception;
	public int addStudent(Student student) throws Exception;
	public void setRegisteredTrue(int studentId) throws Exception;
}
