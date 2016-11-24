package com.project.batacademy.resthandlers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.project.batacademy.domain.Faculty;
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

		faculty = lookupFaculty(id);

		return faculty;
	}

	private Faculty lookupFaculty(int id) {
		Faculty faculty;

		faculty = facultyService.getFacultyDetails(id);

		return faculty;
	}

}
