package com.project.batacademy.resthandlers;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
import com.project.batacademy.domain.UserAccount;
import com.project.batacademy.exceptions.InvalidAcctException;
import com.project.batacademy.exceptions.UnknownResourceException;
import com.project.batacademy.services.FacultyService;
import com.project.batacademy.services.UserService;

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

		try {
			faculty = facultyService.getFacultyDetails(id);
		} catch (Exception e) {
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

		try {
			listOfCourses = facultyService.getCoursesTaughtByFaculty(id);

		} catch (Exception e) {

			throw new UnknownResourceException(
					"Faculty id: " + id + " is invalid or the faculty isn't teaching any courses!");
		}

		return listOfCourses;
	}

	/*
	 * update activity scores of the student taught by the faculty To test, you
	 * can PUT the activity as below: json: {"id": {"studentId": 1002,
	 * "courseId": 13}, "a1": 100, "a2": 100, "a3": 100} xml:
	 * <activity><id><studentId>1002</studentId><courseId>13</courseId></id><a1>
	 * 100</a1><a2>100</a2><a3>100</a3></activity> URL:
	 * http://localhost:8080/batacademy/webservices/facultyrest/faculty/{
	 * facultyId}/activity
	 */
	@PUT
	@Path("/faculty/{facultyId}/activity")
	@Produces("application/xml, application/json")
	@Consumes("application/json, application/xml")
	public Response updateActivityScores(@PathParam("facultyId") int facultyId, Activity activity,
			@HeaderParam("Authorization") String auth) {

		ResponseBuilder respBuilder;
		/* Authorize the user, before proceeding */
		lookupFacultyWithAuth(facultyId, auth);
		try {
			int noOfRowsUpdated = facultyService.updateActivityScores(activity, facultyId);
			logger.info("Successfully updated Student activity scores: " + activity);
			respBuilder = Response.status(Status.CREATED);
			respBuilder.entity(activity);
			return respBuilder.build();
		} catch(Exception e) {
			throw new UnknownResourceException("no rows were updated");
		}
	}

	/* Only return the faculty if the authentication information is correct */
	private void lookupFacultyWithAuth(int facultyId, String auth) {

		try {
			Faculty expectedFaculty = facultyService.getFacultyWithPassword(facultyId);
			String expectedName = expectedFaculty.getFirstName();
			String expectedPassword = expectedFaculty.getPassword();
			String actualAcctName;
			String actualPasswd;

			UserAccount acct = UserService.extractAcctFromAuthorization(auth);
			if (acct == null) {
				logger.debug("Authorization Header was null");
				throw new InvalidAcctException("Invalid Authorization Header");
			}

			/*
			 * Need to verify this user account by looking up the account info
			 * in the database.
			 */
			logger.debug("Authorized user found in lookupFacultyWithAuth():  " + acct);
			actualAcctName = acct.getName();
			actualPasswd = acct.getPassword();
			if (!(actualAcctName.equals(expectedName) && actualPasswd.equals(expectedPassword))) {
				throw new InvalidAcctException("Authorization is invalid for account: " + actualAcctName);
			}

		} catch (Exception e) {
			throw new UnknownResourceException("no faculty with id: " + facultyId);
		}

	}

}
