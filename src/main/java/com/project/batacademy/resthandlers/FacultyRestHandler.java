package com.project.batacademy.resthandlers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.exceptions.UnknownResourceException;
import com.project.batacademy.services.FacultyService;

@Path("/facultyrest")
public class FacultyRestHandler {
	
	@Autowired
	@Qualifier("facultyServiceImpl")
	private FacultyService facultyService;
	private Logger logger = Logger.getLogger(FacultyRestHandler.class);
	
	@GET
	@Path("/faculty/{id}")
	@Produces("application/xml, application/json")
	public Faculty getFaculty(@PathParam("id") int id) {
		Faculty faculty = null;

		faculty = facultyService.getFacultyDetails(id);
		
		if (faculty == null) {
			throw new UnknownResourceException("Faculty id: " + id + " is invalid");
		}

		return faculty;
	}
	
	@GET
	@Path("/faculty/courses/{id}")
	@Produces("application/xml, application/json")
	public List<Course> getCoursestaughtByFaculty(@PathParam("id") int id) {
		List<Course> listOfCourses = null;

		listOfCourses = facultyService.getCoursesTaughtByFaculty(id);
		
		if (listOfCourses == null) {
			throw new UnknownResourceException("Faculty id: " + id + " is invalid or the faculty isn't teaching any courses!");
		}

		return listOfCourses;
	}

}
