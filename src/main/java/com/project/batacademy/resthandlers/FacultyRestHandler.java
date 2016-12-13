package com.project.batacademy.resthandlers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.project.batacademy.domain.Activity;
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

	/* faculty details */
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

	/* courses taught by faculty */
	@GET
	@Path("/faculty/courses/{id}")
	@Produces("application/xml, application/json")
	public List<Course> getCoursestaughtByFaculty(@PathParam("id") int id) {
		List<Course> listOfCourses = null;

		listOfCourses = facultyService.getCoursesTaughtByFaculty(id);

		if (listOfCourses.size() == 0) {
			throw new UnknownResourceException(
					"Faculty id: " + id + " is invalid or the faculty isn't teaching any courses!");
		}

		return listOfCourses;
	}

	/* update activity scores of the student taught by the faculty 
	 * To test, you can PUT the activity as below:
	 * json: {"id": {"studentId": 1002, "courseId": 13}, "a1": 100, "a2": 100, "a3": 100}
	 * xml: <activity><id><studentId>1002</studentId><courseId>13</courseId></id><a1>100</a1><a2>100</a2><a3>100</a3></activity>
	 * URL:  http://localhost:8080/batacademy/webservices/facultyrest/faculty/{facultyId}/activity
	 * */
	@POST
	@Path("/faculty/{facultyId}/activity")
	@Produces("application/xml, application/json")
	@Consumes("application/json, application/xml")
	public Response updateActivityScores(@PathParam("facultyId") int facultyId, Activity activity) {
		
		ResponseBuilder respBuilder;
		
		int noOfRowsUpdated = facultyService.updateActivityScores(activity, facultyId);
		
		if(noOfRowsUpdated == 0) {
			throw new UnknownResourceException("no rows were updated");
		}

		logger.info("Successfully updated Student activity scores: " + activity);
		respBuilder = Response.status(Status.CREATED);
		respBuilder.entity(activity);
		return respBuilder.build();
	}

}
