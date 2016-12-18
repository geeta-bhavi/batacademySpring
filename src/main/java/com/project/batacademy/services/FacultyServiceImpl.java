package com.project.batacademy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.batacademy.dao.FacultyDao;
import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;

@Service("facultyServiceImpl")
@Transactional(readOnly = true)
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	@Qualifier("facultyDaoJdbc")
	private FacultyDao facultyDao;

	@Autowired
	@Qualifier("courseServiceImpl")
	private CourseService courseService;

	@Autowired
	@Qualifier("activityServiceImpl")
	private ActivityService activityService;

	@Autowired
	@Qualifier("studentServiceImpl")
	private StudentService studentService;
	
	@Autowired
	@Qualifier("registeredCoursesServiceImpl")
	private RegisteredCoursesService registeredCoursesService;

	@Override
	public Faculty getFacultyDetails(int facultyId) throws Exception {
		Faculty faculty = (Faculty) facultyDao.getFacultyDetails(facultyId);
		return faculty;
	}

	@Override
	public List<Course> getCoursesTaughtByFaculty(int facultyId) throws Exception {
		List<Course> listOfCourses = courseService.getCoursesTaughtByFaculty(facultyId);
		return listOfCourses;
	}

	@Override
	@Transactional(readOnly = false)
	public int updateActivityScores(Activity activity, int facultyId) throws Exception {
		/*
		 * first check if a row exists in activity table for that student and
		 * course
		 */
		int studentId = activity.getId().getStudentId();
		int courseId = activity.getId().getCourseId();
		int noOfRowsUpdated = 0;
		Activity a1 = activityService.getActivityDetails(studentId, courseId);

		/*
		 * if a row does exist, update the activity scores or else insert a new
		 * row
		 */
		if (a1 != null) {
			noOfRowsUpdated = activityService.updateActivityScores(activity, facultyId);
		} else {
			noOfRowsUpdated = activityService.insertActivity(activity, facultyId);
		}

		return noOfRowsUpdated;
	}

	@Override
	public Faculty getFacultyWithPassword(int facultyId) throws Exception {
		Faculty faculty = (Faculty) facultyDao.getFacultyWithPassword(facultyId);
		return faculty;
	}

	@Override
	@Transactional(readOnly = false)
	public void processEnableDisableRegistration() throws Exception {
		/*
		 * calculate cgpa, make completed column in RegisteredCourses as true
		 * and Register column in Student table as false
		 */
		studentService.updateAllStudentsGPA();
		registeredCoursesService.updateCompletedColumn(true);
		studentService.updateRegisteredColumn(false);

	}

	@Override
	@Transactional(readOnly = false)
	public void updateEnableColumn(boolean enabled) throws Exception {
		facultyDao.updateEnableColumn(enabled);
		
	}
}
