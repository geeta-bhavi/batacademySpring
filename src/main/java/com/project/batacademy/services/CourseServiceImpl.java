package com.project.batacademy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.CourseDao;
import com.project.batacademy.domain.Course;

@Service("courseServiceImpl")
public class CourseServiceImpl implements CourseService {

	@Autowired
	@Qualifier("courseDaoJdbc")
	private CourseDao courseDao;

	@Override
	public List<Course> getCoursesTaughtByFaculty(int facultyId) throws Exception {
		List<Course> listOfCourses = courseDao.getCoursesTaughtByFaculty(facultyId);
		return listOfCourses;
	}

	@Override
	public List<Course> getRemainingCourses(List<Integer> coursesTaken) throws Exception {
		return courseDao.getRemainingCourses(coursesTaken);
	}

}
