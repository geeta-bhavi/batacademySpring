package com.project.batacademy.services;

import java.util.List;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;

public interface FacultyService {
	public Faculty getFacultyDetails(int facultyId);
	public List<Course> getCoursesTaughtByFaculty(int facultyId);
	public int updateActivityScores(Activity activity, int facultyId);
}
