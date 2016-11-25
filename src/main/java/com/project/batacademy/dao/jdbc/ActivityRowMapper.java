package com.project.batacademy.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityId;

public class ActivityRowMapper implements RowMapper<Activity> {

	@Override
	public Activity mapRow(ResultSet resultSet, int row) throws SQLException {
		
		Activity activity;
		ActivityId activityId;
		int studentId;
		int courseId;
		int activity1;
		int activity2;
		int activity3;
		
		studentId = resultSet.getInt("studentId");
		courseId = resultSet.getInt("courseId");
		activityId = new ActivityId(studentId, courseId);
		activity1 = resultSet.getInt("a1");
		activity2 = resultSet.getInt("a2");
		activity3 = resultSet.getInt("a3");
		
		activity = new Activity();
		activity.setId(activityId);
		activity.setA1(activity1);
		activity.setA2(activity2);
		activity.setA3(activity3);
		
		return activity;
	}

}
