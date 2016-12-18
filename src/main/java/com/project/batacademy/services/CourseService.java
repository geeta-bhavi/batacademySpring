package com.project.batacademy.services;

import java.util.List;

import com.project.batacademy.domain.Course;

public interface CourseService {
	public List<Course> getCoursesTaughtByFaculty(int facultyId) throws Exception;
}
