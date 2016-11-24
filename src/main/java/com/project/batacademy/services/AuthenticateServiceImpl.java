package com.project.batacademy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.FacultyDao;
import com.project.batacademy.dao.StudentDao;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.domain.Student;

@Service("authServiceImpl")
public class AuthenticateServiceImpl implements AuthenticateService {
	private static final Logger logger = LoggerFactory.getLogger(AuthenticateService.class);
	@Autowired
	@Qualifier("studentDaoJdbc")
	private StudentDao studentDao;

	@Autowired
	@Qualifier("facultyDaoJdbc")
	private FacultyDao facultyDao;

	public boolean checkIfStudentExists(int userId, String password) {
		try {
			boolean exists = studentDao.authenticateStudent(userId, password);
			return exists;

		} catch (Exception e) {

			return false;
		}

	}

	public boolean checkIfFacultyExists(int userId, String password) {
		try {
			boolean exists = facultyDao.authenticateFaculty(userId, password);
			return exists;
		} catch(Exception e) {
			return false;
		}
		
	}

}
