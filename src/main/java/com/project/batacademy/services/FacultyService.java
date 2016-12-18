package com.project.batacademy.services;

import java.util.List;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;

public interface FacultyService {
	public Faculty getFacultyDetails(int facultyId) throws Exception;
	public Faculty getFacultyWithPassword(int facultyId) throws Exception;
	public List<Course> getCoursesTaughtByFaculty(int facultyId) throws Exception;
	public int updateActivityScores(Activity activity, int facultyId) throws Exception;
	public void processEnableDisableRegistration() throws Exception;
	public void updateEnableColumn(boolean enabled) throws Exception;
}
