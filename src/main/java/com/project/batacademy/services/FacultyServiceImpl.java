package com.project.batacademy.services;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.batacademy.dao.FacultyDao;
import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.domain.RegisteredCourses;

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
	public void processEnableDisableRegistration(boolean enable) throws Exception {
		/*
		 * calculate student's cgpa for the courses being completed, make
		 * completed column in registeredCourses table as true and registered
		 * column in student table as false
		 */
		if (enable) {
			studentService.updateAllStudentsGPA();
			registeredCoursesService.updateCompletedColumn(true);
			studentService.updateRegisteredColumn(false);
		}
		updateEnableColumn(enable);
		

	}

	@Override
	@Transactional(readOnly = false)
	public void updateEnableColumn(boolean enabled) throws Exception {
		facultyDao.updateEnableColumn(enabled);

	}

	@Override
	public boolean isRegisterationEnabled() throws Exception {
		return facultyDao.isRegisterationEnabled();
	}

	@Override
	public HashMap<Integer, String> getFacultyName() throws Exception {
		HashMap<Integer, String> facultyMap = new HashMap();
        List<Faculty> facultyList = facultyDao.getAllFaculty();
        facultyMap.clear();
        for (Faculty faculty : facultyList) {
            facultyMap.put(faculty.getFacultyId(), faculty.getFirstName());
        }
        return facultyMap;
	}

	@Override
	public String getFacultyNameForAGivenCourseID(int courseId) throws Exception {
		return facultyDao.getFacultyNameForAGivenCourseID(courseId);
	}
}
