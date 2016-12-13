package com.project.batacademy.dao;

import com.project.batacademy.domain.Activity;

public interface ActivityDao {
	
	public Activity getActivityDetails(int studentId, int courseId) throws Exception;
	public int updateActivityScores(Activity activity, int facultyId) throws Exception;
	public int insertActivity(Activity activity, int facultyId) throws Exception;
}
