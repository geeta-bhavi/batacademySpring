package com.project.batacademy.resthandlers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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

		stud = lookupStudent(id);

		return stud;
	}

	private Student lookupStudent(int id) {
		Student student;

		student = studentService.getStudentDetails(id);

		return student;
	}
}
