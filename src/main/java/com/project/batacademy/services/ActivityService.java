package com.project.batacademy.services;

import java.util.List;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.RegisteredCoursesId;

public interface ActivityService {
	public Activity getActivityDetails(int studentId, int courseId) throws Exception;
	public int updateActivityScores(Activity activity, int facultyId) throws Exception;
	public int insertActivity(Activity activity, int facultyId) throws Exception;
	public List<Activity> getActivitiesOfNotCompletedCourses(List<RegisteredCoursesId> notCompletedCourses) throws Exception;
}
