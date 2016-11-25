package com.project.batacademy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.ActivityDao;
import com.project.batacademy.dao.RegisteredCoursesDao;
import com.project.batacademy.dao.StudentDao;
import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.Student;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService {

	@Autowired
	@Qualifier("studentDaoJdbc")
	private StudentDao studentDao;
	
	@Autowired
	@Qualifier("activityDaoJdbc")
	private ActivityDao activityDao;
	
	@Autowired
	@Qualifier("registeredCoursesDaoJdbc")
	private RegisteredCoursesDao registeredCoursesDao;

	public Student getStudentDetails(int studentId) {
		try {
			Student student = (Student) studentDao.getStudentDetails(studentId);
			return student;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Activity getActivityDetails(int studentId, int courseId) {
		try {
			Activity activity = (Activity) activityDao.getActivityDetails(studentId, courseId);
			return activity;
		} catch (Exception e) {
			return null;
		}
	}
	
	@Override
	public RegisteredCourses registeredCourseOfStudent(int studentId, int courseId) {
		try {
			RegisteredCourses regCourses = registeredCoursesDao.registeredCourseOfStudent(studentId, courseId);
			return regCourses;
		} catch (Exception e) {
			return null;
		}
	}

}
