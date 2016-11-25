package com.project.batacademy.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.RegisteredCoursesId;

public class RegisteredCoursesRowMapper implements RowMapper<RegisteredCourses> {

	@Override
	public RegisteredCourses mapRow(ResultSet resultSet, int row) throws SQLException {

		RegisteredCourses registeredCourses;
		RegisteredCoursesId registeredCoursesId;
		int courseId;
		int studentId;
		String courseName;
		boolean completed;

		courseId = resultSet.getInt("courseId");
		studentId = resultSet.getInt("studentId");
		registeredCoursesId = new RegisteredCoursesId(courseId, studentId);
		courseName = resultSet.getString("courseName");
		completed = resultSet.getBoolean("completed");

		registeredCourses = new RegisteredCourses();
		registeredCourses.setId(registeredCoursesId);
		registeredCourses.setCourseName(courseName);
		registeredCourses.setCompleted(completed);

		return registeredCourses;
	}

}
