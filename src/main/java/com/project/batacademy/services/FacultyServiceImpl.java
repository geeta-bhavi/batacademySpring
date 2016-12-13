package com.project.batacademy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.ActivityDao;
import com.project.batacademy.dao.CourseDao;
import com.project.batacademy.dao.FacultyDao;
import com.project.batacademy.dao.RegisteredCoursesDao;
import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.domain.RegisteredCourses;

@Service("facultyServiceImpl")
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	@Qualifier("facultyDaoJdbc")
	private FacultyDao facultyDao;

	@Autowired
	@Qualifier("courseDaoJdbc")
	private CourseDao courseDao;

	@Autowired
	@Qualifier("activityDaoJdbc")
	private ActivityDao activityDao;

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

	@Override
	public int updateActivityScores(Activity activity, int facultyId) {
		/*
		 * first check if a row exists in activity table for that student and
		 * course
		 */
		int studentId = activity.getId().getStudentId();
		int courseId = activity.getId().getCourseId();
		int noOfRowsUpdated = 0;
		try {
			Activity a1 = activityDao.getActivityDetails(studentId, courseId);
			
			/* if a row does exist, update the activity scores or else insert a new row */
			if (a1 != null) {
				noOfRowsUpdated = activityDao.updateActivityScores(activity, facultyId);
			} else {
				noOfRowsUpdated = activityDao.insertActivity(activity, facultyId);
			}

			return noOfRowsUpdated;
		} catch (Exception e) {
			return 0;
		}
	}
}
