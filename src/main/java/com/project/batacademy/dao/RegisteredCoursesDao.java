package com.project.batacademy.dao;

import java.util.List;

import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.RegisteredCoursesId;

public interface RegisteredCoursesDao {
	
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId) throws Exception;
	public List<RegisteredCoursesId> getNotCompletedCoursesOfStudents() throws Exception;
	public void updateCompletedColumn(boolean completed) throws Exception;
}
