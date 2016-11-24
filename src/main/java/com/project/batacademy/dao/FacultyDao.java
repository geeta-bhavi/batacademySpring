package com.project.batacademy.dao;

import com.project.batacademy.domain.Faculty;

public interface FacultyDao {
	
	public Faculty getFacultyDetails(int userId, String pwd);
	public Faculty getFacultyDetails(int facultyId);


}
