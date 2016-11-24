package com.project.batacademy.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.batacademy.domain.Course;


public class CourseRowMapper implements RowMapper<Course> {

	@Override
	public Course mapRow(ResultSet resultSet, int row) throws SQLException {
		Course course = null;
		
		course = new Course();
		course.setCourseId(resultSet.getInt("courseId"));
		course.setFacultyId(resultSet.getInt("facultyId"));
		course.setCourseName(resultSet.getString("courseName"));
		course.setCredits(resultSet.getInt("credits"));
		
		return course;
	}

}
