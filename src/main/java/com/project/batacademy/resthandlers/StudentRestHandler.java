package com.project.batacademy.resthandlers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.Student;
import com.project.batacademy.exceptions.UnknownResourceException;
import com.project.batacademy.services.StudentService;

@Path("/studrest")
public class StudentRestHandler {

	@Autowired
	@Qualifier("studentServiceImpl")
	private StudentService studentService;
	
	private Logger logger = Logger.getLogger(StudentRestHandler.class);

	/*
	 * Test Url:
	 * http://localhost:8080/batacademy/webservices/studrest/student/100
	 */
	@GET
	@Path("/student/{id}")
	@Produces("application/xml, application/json")
	public Student getStudent(@PathParam("id") int id) {
		Student stud = null;

		stud = studentService.getStudentDetails(id);

		if (stud == null) {
			throw new UnknownResourceException("Student id: " + id + " is invalid");
		}

		return stud;
	}
	
	/* activity scores based on student id and course id */
	@GET
	@Path("/student/{studentId}/course/{courseId}")
	@Produces("application/xml, application/json")
	public ActivityCompletion getActivity(@PathParam("studentId") int studentId, @PathParam("courseId") int courseId) {
		ActivityCompletion activityCompletion = null;
		Activity activity = studentService.getActivityDetails(studentId, courseId);
		
		if(activity == null) {
			throw new UnknownResourceException("Student id: " + studentId + " or course id: "+courseId+" is invalid or student has no activities yet!");
		}		
		RegisteredCourses regCourse = studentService.registeredCourseOfStudent(studentId, courseId);
		
		if(regCourse == null) {
			throw new UnknownResourceException("Student id: " + studentId + " has not registered to the course id: "+courseId);
		}
		
		activityCompletion = new ActivityCompletion(activity, regCourse.isCompleted());	
		
		return activityCompletion;
		
	}

	/* get all courses taken by student */
}
