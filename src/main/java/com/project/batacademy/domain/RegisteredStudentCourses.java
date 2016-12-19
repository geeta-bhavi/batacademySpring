package com.project.batacademy.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RegisteredStudentCourses {
	private int studentId;
	private ArrayList<Map<?,?>> courseList;
	
	public RegisteredStudentCourses() {
	
	}

	@Override
	public String toString() {
		return "RegisteredStudentCourses [studentId=" + studentId + ", courseList=" + courseList + "]";
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public ArrayList<Map<?, ?>> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<Map<?, ?>> courseList) {
		this.courseList = courseList;
	}


	
	

}
