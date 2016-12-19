package com.project.batacademy.services;

import java.util.List;

import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.RegisteredCoursesId;

public interface RegisteredCoursesService {
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId) throws Exception;
	public List<RegisteredCoursesId> getNotCompletedCoursesOfStudents() throws Exception;
	public void updateCompletedColumn(boolean completed) throws Exception;
	public List<Integer>  getCoursesIdGivenStudentId(int studentId) throws Exception;
	public List<RegisteredCourses> getRegisteredCoursesForStudent(int studentId) throws Exception;
	public void updateRegisteredCourses(List<RegisteredCourses> registeredCoursesList) throws Exception;
}
