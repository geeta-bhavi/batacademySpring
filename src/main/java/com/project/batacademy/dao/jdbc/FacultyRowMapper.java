package com.project.batacademy.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.batacademy.domain.Faculty;


public class FacultyRowMapper implements RowMapper<Faculty> {

	public Faculty mapRow(ResultSet resultSet, int row) throws SQLException {
		
		Faculty faculty = null;
		
		faculty = new Faculty();
		faculty.setFacultyId(resultSet.getInt("facultyId"));
		faculty.setFirstName(resultSet.getString("firstName"));
		faculty.setLastName(resultSet.getString("lastName"));
		faculty.setGender(resultSet.getString("gender"));
		faculty.setPhone(resultSet.getString("phone"));
		faculty.setPassword(resultSet.getString("password"));
		faculty.setDesignation(resultSet.getString("designation"));
		faculty.setEnable(resultSet.getBoolean("enable"));
		
		
		
		return faculty;
	}

}
