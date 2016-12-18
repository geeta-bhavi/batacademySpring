package com.project.batacademy.dao;

import com.project.batacademy.domain.Faculty;

public interface FacultyDao {
	
	public boolean authenticateFaculty(int userId, String pwd) throws Exception;
	public Faculty getFacultyDetails(int facultyId) throws Exception;
	public Faculty getFacultyWithPassword(int facultyId) throws Exception;
	public void updateEnableColumn(boolean enabled) throws Exception;


}
