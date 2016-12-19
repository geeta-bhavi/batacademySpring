package com.project.batacademy.dao;

import java.util.List;

import com.project.batacademy.domain.Faculty;
import com.project.batacademy.domain.RegisteredCourses;

public interface FacultyDao {
	
	public boolean authenticateFaculty(int userId, String pwd) throws Exception;
	public Faculty getFacultyDetails(int facultyId) throws Exception;
	public Faculty getFacultyWithPassword(int facultyId) throws Exception;
	public void updateEnableColumn(boolean enabled) throws Exception;
	public boolean isRegisterationEnabled() throws Exception;
	public List<Faculty> getAllFaculty() throws Exception;
	public String getFacultyNameForAGivenCourseID(int courseId) throws Exception;
}
