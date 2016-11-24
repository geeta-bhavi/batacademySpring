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

	public String checkIfStudentExists(int userId, String password) {
		Student student = (Student) studentDao.getStudentDetails(userId, password);
        if (student != null) {
            return "student";
        }
        return "error";
	}

	public String checkIfFacultyExists(int userId, String password) {
		Faculty faculty = (Faculty) facultyDao.getFacultyDetails(userId, password);
        if (faculty != null) {
            return "faculty";
        }
        return "error";
	}

}
