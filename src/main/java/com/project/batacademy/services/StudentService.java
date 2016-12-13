package com.project.batacademy.services;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.Student;

public interface StudentService {
	
	public Student getStudentDetails(int studentId);
	public Activity getActivityDetails(int studentId, int courseId);
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId);
	public ActivityCompletion getActivityAndCompletedState(int studentId, int courseId);
	public int removeStudentWithId(int studentId);
}
