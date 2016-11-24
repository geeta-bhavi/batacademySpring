package com.project.batacademy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.CourseDao;
import com.project.batacademy.dao.FacultyDao;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;

@Service("facultyServiceImpl")
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	@Qualifier("facultyDaoJdbc")
	private FacultyDao facultyDao;

	@Autowired
	@Qualifier("courseDaoJdbc")
	private CourseDao courseDao;

	@Override
	public Faculty getFacultyDetails(int facultyId) {
		try {
			Faculty faculty = (Faculty) facultyDao.getFacultyDetails(facultyId);
			return faculty;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public List<Course> getCoursesTaughtByFaculty(int facultyId) {
		try {
			List<Course> listOfCourses = courseDao.getCoursesTaughtByFaculty(facultyId);
			return listOfCourses;
		} catch (Exception e) {
			return null;
		}
	}

}
