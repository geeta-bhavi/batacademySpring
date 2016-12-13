package com.project.batacademy.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.ActivityDao;
import com.project.batacademy.dao.RegisteredCoursesDao;
import com.project.batacademy.dao.StudentDao;
import com.project.batacademy.dao.jdbc.StudentDaoJdbcImpl;
import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.Student;
import com.project.batacademy.exceptions.UnknownResourceException;

@Service("studentServiceImpl")
public class StudentServiceImpl implements StudentService {
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

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
			return new Student();
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
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId) {
		try {
			RegisteredCourses regCourses = registeredCoursesDao.getRegisteredCoursesOfStudent(studentId, courseId);
			return regCourses;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public ActivityCompletion getActivityAndCompletedState(int studentId, int courseId) {
		
		try {
		
			ActivityCompletion activityCompletion = null;
			Activity activity = null;
			
			/* check if student has registered for the course */
			RegisteredCourses regCourse = getRegisteredCoursesOfStudent(studentId, courseId);
			
			if(regCourse != null) {
				activity = getActivityDetails(studentId, courseId);
			}
			if(activity == null) {
				activity = new Activity();
			}
			
			activityCompletion = new ActivityCompletion(activity, regCourse.getId(), regCourse.isCompleted());
			
			return activityCompletion;
			
			
		} catch(Exception e) {
			return new ActivityCompletion();
		}

		
	}
	
	@Override
	public int removeStudentWithId(int studentId) {
		try {
			int result = studentDao.removeStudentWithId(studentId);
			return result;
		} catch(Exception e) {
			return 0;
		}
	}

}
