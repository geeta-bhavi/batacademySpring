package com.project.batacademy.dao.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.batacademy.domain.Student;


public class StudentRowMapper implements RowMapper<Student> {

	
	public Student mapRow(ResultSet resultSet, int row) throws SQLException {
		Student stud;
		String password;
		
		stud = new Student();
		stud.setStudentId(resultSet.getInt("studentId"));
		stud.setFirstName(resultSet.getString("firstName"));
		stud.setLastName(resultSet.getString("lastName"));
		stud.setGender(resultSet.getString("gender"));
		stud.setPhone(resultSet.getString("phone"));
		stud.setRegistered(resultSet.getBoolean("registered"));
		try {
			password = resultSet.getString("password");
		} catch (SQLException e) {
			password = null;
		}
		stud.setPassword(password);
		
		
		
		return stud;
	}

}
