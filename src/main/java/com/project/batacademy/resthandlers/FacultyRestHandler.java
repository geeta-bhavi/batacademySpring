package com.project.batacademy.resthandlers;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityId;
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

	/* update activity score taught by the faculty */
	@PUT
	@Path("/faculty/{facultyId}/studentId/{studentId}/courseId/{courseId}/activity/{a1}/{a2}/{a3}")
	@Produces("application/xml, application/json")
	public int updateActivity(@PathParam("facultyId") int facultyId, @PathParam("studentId") int studentId,
			@PathParam("courseId") int courseId, @PathParam("a1") int a1, @PathParam("a2") int a2,
			@PathParam("a3") int a3) {
		
		int noOfRowsUpdated = 0;
		ActivityId activityId = new ActivityId(studentId, courseId);
		Activity activity = new Activity(activityId, a1, a2, a3);
		
		noOfRowsUpdated = facultyService.updateActivity(activity, facultyId);
		
		if(noOfRowsUpdated == 0) {
			throw new UnknownResourceException("no rows were updated");
		}

		return noOfRowsUpdated;
	}

}
