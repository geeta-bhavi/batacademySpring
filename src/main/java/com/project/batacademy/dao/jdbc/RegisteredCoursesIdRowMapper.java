package com.project.batacademy.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.batacademy.domain.RegisteredCoursesId;

public class RegisteredCoursesIdRowMapper implements RowMapper<RegisteredCoursesId> {

	@Override
	public RegisteredCoursesId mapRow(ResultSet resultSet, int row) throws SQLException {
		RegisteredCoursesId registeredCoursesId;

		int courseId = resultSet.getInt("courseId");
		int studentId = resultSet.getInt("studentId");
		registeredCoursesId = new RegisteredCoursesId(courseId, studentId);

		return registeredCoursesId;
	}

}
