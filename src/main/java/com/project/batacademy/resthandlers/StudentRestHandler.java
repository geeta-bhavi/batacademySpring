package com.project.batacademy.resthandlers;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.project.batacademy.domain.ActivityCompletion;
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
	 * http://localhost:8080/batacademy/webservices/studrest/student/1001
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
	
	/* get activity based on student id and course id 
	 * http://localhost:8080/batacdemy/webservices/studrest/student/1002/course/13
	 * */
	@GET
	@Path("/student/{studentId}/course/{courseId}")
	@Produces("application/xml, application/json")
	public ActivityCompletion getActivityAndCompletedState(@PathParam("studentId") int studentId, @PathParam("courseId") int courseId) {
		ActivityCompletion activityCompletion = null;
		
		activityCompletion = studentService.getActivityAndCompletedState(studentId, courseId);
		
		if (activityCompletion == null) {
			throw new UnknownResourceException("No actvites yet for student id: "+studentId+" with this course id: "+courseId);
		}
		
		return activityCompletion;
		
	}
	
	/* Test Url:  Use HTTP Delete command
	 * http://localhost:8080/batacademy/webservices/studrest/student/1002
	 */
	@DELETE
	@Path("/student/{id}")
	public Response deleteStudent(@PathParam("id") int id) {
		int removedStud;
		ResponseBuilder respBuilder;
		
		removedStud = studentService.removeStudentWithId(id);
		logger.info("student removed "+removedStud);
		
		
		if (removedStud == 0) {
			respBuilder = Response.status(Status.NOT_FOUND);
		} else {
			respBuilder = Response.ok();
		}
		return respBuilder.build();
	}

	/* get all courses taken by student */
}
