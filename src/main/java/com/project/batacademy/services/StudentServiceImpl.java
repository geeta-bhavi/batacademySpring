package com.project.batacademy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.StudentDao;
import com.project.batacademy.domain.Student;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService {

	@Autowired
	@Qualifier("studentDaoJdbc")
	private StudentDao studentDao;

	public Student getStudentDetails(int studentId) {
		try {
			Student student = (Student) studentDao.getStudentDetails(studentId);
			return student;
		} catch (Exception e) {
			return null;
		}
	}

}
