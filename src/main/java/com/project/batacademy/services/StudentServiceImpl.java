package com.project.batacademy.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.batacademy.dao.StudentDao;
import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.ActivityId;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.RegisteredCoursesId;
import com.project.batacademy.domain.SelectedCoursesBean;
import com.project.batacademy.domain.Student;

@Service("studentServiceImpl")
@Transactional(readOnly = true)
public class StudentServiceImpl implements StudentService {
	private static final Logger logger = LoggerFactory.getLogger(StudentServiceImpl.class);

	@Autowired
	@Qualifier("studentDaoJdbc")
	private StudentDao studentDao;

	@Autowired
	@Qualifier("activityServiceImpl")
	private ActivityService activityService;

	@Autowired
	@Qualifier("registeredCoursesServiceImpl")
	private RegisteredCoursesService registeredCoursesService;

	@Autowired
	@Qualifier("courseServiceImpl")
	private CourseService courseService;

	@Autowired
	@Qualifier("facultyServiceImpl")
	private FacultyService facultyService;

	public Student getStudentDetails(int studentId) throws Exception {
		Student student = (Student) studentDao.getStudentDetails(studentId);
		return student;
	}

	@Override
	public Activity getActivityDetails(int studentId, int courseId) throws Exception {
		Activity activity = (Activity) activityService.getActivityDetails(studentId, courseId);
		return activity;
	}

	@Override
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId) throws Exception {
		RegisteredCourses regCourses = registeredCoursesService.getRegisteredCoursesOfStudent(studentId, courseId);
		return regCourses;
	}

	@Override
	public ActivityCompletion getActivityAndCompletedState(int studentId, int courseId) throws Exception {

		ActivityCompletion activityCompletion = new ActivityCompletion();
		Activity activity = null;

		/* check if student has registered for the course */
		RegisteredCourses regCourse = getRegisteredCoursesOfStudent(studentId, courseId);

		activity = getActivityDetails(studentId, courseId);
		activity = new Activity();

		activityCompletion = new ActivityCompletion(activity, regCourse.getId(), regCourse.isCompleted());

		return activityCompletion;

	}

	@Override
	@Transactional(readOnly = false)
	public int removeStudentWithId(int studentId) throws Exception {
		int result = studentDao.removeStudentWithId(studentId);
		return result;
	}

	@Override
	public Student getStudentWithPassword(int studentId) throws Exception {
		Student student = (Student) studentDao.getStudentWithPassword(studentId);
		return student;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateAllStudentsGPA() throws Exception {

		List<RegisteredCoursesId> notCompletedCourses = registeredCoursesService.getNotCompletedCoursesOfStudents();
		System.out.println("courses " + notCompletedCourses);
		if (notCompletedCourses.size() > 0) {
			List<Activity> activities = activityService.getActivitiesOfNotCompletedCourses(notCompletedCourses);
			if (activities.size() > 0) {
				System.out.println("activities " + activities);
				Map<ActivityId, Integer> gradeMap = new HashMap<ActivityId, Integer>();
				Map<Integer, Float> gpa = new HashMap<Integer, Float>();

				/* calculate grade based on activity score */
				for (Activity act : activities) {
					ActivityId id = act.getId();
					int grade = 0;
					float avg = ((act.getA1() + act.getA2() + act.getA3()) / 300.0f) * 100;

					if (isBetween(avg, 95, 100)) {
						grade = 4;
					} else if (isBetween(avg, 75, 94)) {
						grade = 3;
					} else if (isBetween(avg, 65, 74)) {
						grade = 2;
					}
					logger.info("avg " + avg + ", grade " + grade);
					gradeMap.put(id, grade);
				}

				/*
				 * calculate current sem gpa based on the grades received in
				 * each course for a particular student
				 */
				for (Map.Entry<ActivityId, Integer> entry : gradeMap.entrySet()) {
					int key = entry.getKey().getStudentId();
					float value = entry.getValue();

					System.out.println("studentid " + key + ", grade " + value);

					if (!gpa.containsKey(key)) {
						gpa.put(key, value);
					} else {
						float prevValue = gpa.get(key);
						value = (prevValue + value) / 2.0f;
						gpa.put(key, value);
					}
					logger.info("studentid " + key + ", gpa " + value);
				}

				/*
				 * get cgpa for a student and then based on current sem gpa -
				 * calculate cgpa again
				 */
				for (Map.Entry<Integer, Float> entry : gpa.entrySet()) {
					int key = entry.getKey();
					float cgpa = getStudentCGPA(key);
					float value = entry.getValue();
					float finalCGPA = value;
					if (cgpa > 0) {
						finalCGPA = (cgpa + value) / 2.0f;
					}
					updateStudentCGPA(key, finalCGPA);

				}
			}
		}

	}

	private float getStudentCGPA(int studentId) throws Exception {
		float cgpa = studentDao.getStudentCGPA(studentId);
		return cgpa;
	}

	@Transactional(readOnly = false)
	private void updateStudentCGPA(int studentId, float cgpa) throws Exception {
		studentDao.updateStudentCGPA(studentId, cgpa);
	}

	@Override
	@Transactional(readOnly = false)
	public void updateRegisteredColumn(boolean registered) throws Exception {
		studentDao.updateRegisteredColumn(registered);
	}

	private static boolean isBetween(float x, float lower, float upper) {
		return lower <= x && x <= upper;
	}

	@Override
	@Transactional(readOnly = false)
	public int addStudent(Student student) throws Exception {
		return studentDao.addStudent(student);
	}

	@Override
	public List<Course> getRemainingCourses(int studentId) throws Exception {
		List<Integer> coursesTaken = registeredCoursesService.getCoursesIdGivenStudentId(studentId);
		if (coursesTaken.size() <= 0) {
			coursesTaken = new ArrayList<Integer>();
			coursesTaken.add(0);
		}
		logger.info("courses taken by student: " + coursesTaken);
		List<Course> courses = courseService.getRemainingCourses(coursesTaken);
		logger.info("remaining courses: " + courses);
		return courses;
	}

	@Override
	public List<SelectedCoursesBean> getRegisteredCourses(int studentId) throws Exception {
		List<RegisteredCourses> registeredCourses = registeredCoursesService.getRegisteredCoursesForStudent(studentId);

		ArrayList<SelectedCoursesBean> selectedCourses = new ArrayList<SelectedCoursesBean>();
		for (RegisteredCourses registeredCourse : registeredCourses) {
			int courseId = registeredCourse.getId().getCourseId();

			Activity activityObj = (Activity) activityService.getActivityforGiveCouseAndStudent(courseId, studentId);

			SelectedCoursesBean selectedCourse = new SelectedCoursesBean();
			selectedCourse.setCourseID(registeredCourse.getId().getCourseId());
			selectedCourse.setCourseName(registeredCourse.getCourseName());
			selectedCourse.setCompleted(registeredCourse.isCompleted());
			selectedCourse.setFacultyName(facultyService.getFacultyNameForAGivenCourseID(courseId));

			if (null != activityObj) {
				selectedCourse.setA1(activityObj.getA1());
				selectedCourse.setA2(activityObj.getA2());
				selectedCourse.setA3(activityObj.getA3());
			} else {
				selectedCourse.setA1(0);
				selectedCourse.setA2(0);
				selectedCourse.setA3(0);
			}

			selectedCourses.add(selectedCourse);
		}

		return selectedCourses;
	}

	@Override
	@Transactional(readOnly = false)
	public void updateRegisteredCourses(List<RegisteredCourses> registeredCoursesList) throws Exception {

		registeredCoursesService.updateRegisteredCourses(registeredCoursesList);

	}

	@Override
	@Transactional(readOnly = false)
	public void setRegisteredTrue(int studentId) throws Exception {
		studentDao.setRegisteredTrue(studentId);
	}

}
