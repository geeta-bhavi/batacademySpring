package com.project.batacademy.dao;

import com.project.batacademy.domain.RegisteredCourses;

public interface RegisteredCoursesDao {
	
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId) throws Exception;

}
