package com.project.batacademy.services;

import java.util.List;
import java.util.Map;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.SelectedCoursesBean;
import com.project.batacademy.domain.Student;

public interface StudentService {
	
	public Student getStudentDetails(int studentId) throws Exception;
	public Student getStudentWithPassword(int studentId) throws Exception;
	public Activity getActivityDetails(int studentId, int courseId) throws Exception;
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId) throws Exception;
	public ActivityCompletion getActivityAndCompletedState(int studentId, int courseId) throws Exception;
	public int removeStudentWithId(int studentId) throws Exception;
	public void updateAllStudentsGPA() throws Exception;
	public void updateRegisteredColumn(boolean registered) throws Exception;
	public int addStudent(Student student) throws Exception;
	public List<Course> getRemainingCourses(int studentId) throws Exception;
	public List<SelectedCoursesBean> getRegisteredCourses(int studentId) throws Exception;
	public void updateRegisteredCourses(List<RegisteredCourses> registeredCoursesList) throws Exception;
	public void setRegisteredTrue(int studentId) throws Exception;
}
