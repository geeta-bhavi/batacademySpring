package com.project.batacademy.dao;

import java.util.List;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.RegisteredCoursesId;

public interface ActivityDao {

	public Activity getActivityDetails(int studentId, int courseId) throws Exception;

	public int updateActivityScores(Activity activity, int facultyId) throws Exception;

	public int insertActivity(Activity activity, int facultyId) throws Exception;

	public List<Activity> getActivitiesOfNotCompletedCourses(List<RegisteredCoursesId> notCompletedCourses)
			throws Exception;

	public Activity getActivityforGiveCouseAndStudent(int courseId, int studentId) throws Exception;
}
