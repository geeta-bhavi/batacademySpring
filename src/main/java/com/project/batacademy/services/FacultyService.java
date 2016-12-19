package com.project.batacademy.services;

import java.util.HashMap;
import java.util.List;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.domain.RegisteredCourses;

public interface FacultyService {
	public Faculty getFacultyDetails(int facultyId) throws Exception;
	public Faculty getFacultyWithPassword(int facultyId) throws Exception;
	public List<Course> getCoursesTaughtByFaculty(int facultyId) throws Exception;
	public int updateActivityScores(Activity activity, int facultyId) throws Exception;
	public void processEnableDisableRegistration(boolean enable) throws Exception;
	public void updateEnableColumn(boolean enabled) throws Exception;
	public boolean isRegisterationEnabled() throws Exception;
	public HashMap<Integer, String> getFacultyName() throws Exception;
	public String getFacultyNameForAGivenCourseID(int courseId) throws Exception;
}
