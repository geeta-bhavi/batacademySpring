package com.project.batacademy.dao;

import java.util.List;

import com.project.batacademy.domain.Course;

public interface CourseDao {
	
	public List<Course> getCoursesTaughtByFaculty(int facultyId) throws Exception;
}
