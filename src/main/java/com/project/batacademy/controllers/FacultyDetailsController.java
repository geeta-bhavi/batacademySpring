package com.project.batacademy.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.ActivityCompletion;
import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.services.FacultyService;
import com.project.batacademy.services.StudentService;

@Controller
public class FacultyDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(FacultyDetailsController.class);
	private static String STUDENT_SERVICES_URL = "http://localhost:8080/batacademy/webservices/studrest/student/";
	private static Client client = null;

	@Autowired
	@Qualifier("facultyServiceImpl")
	FacultyService facultyService;

	@Autowired
	@Qualifier("studentServiceImpl")
	StudentService studentservice;

	@RequestMapping(value = "/facultyDetailsController", method = RequestMethod.GET)
	public ModelAndView facultyDetails(HttpSession session) {

		ModelAndView modelView = new ModelAndView("home");

		if (session != null && session.getAttribute("facultyId") != null) {
			int facultyId = (Integer) session.getAttribute("facultyId");
			Faculty faculty = null;
			List<Course> listOfCourses = null;

			faculty = facultyService.getFacultyDetails(facultyId);

			if (faculty != null) {
				listOfCourses = facultyService.getCoursesTaughtByFaculty(facultyId);

				/*
				 * if president signed in, then go to president details page
				 */
				if (facultyId == 1) {
					modelView = new ModelAndView("presidentDetails");
				} else {
					modelView = new ModelAndView("facultyDetails");
				}

				modelView.addObject("faculty", faculty);
				modelView.addObject("courses", listOfCourses);

			}
		}
		return modelView;
	}

	@RequestMapping(value = "/facultyDetailsController/search", method = RequestMethod.POST)
	@ResponseBody
	public ActivityCompletion searchStudentByIdAndCourse(@RequestHeader(value = "Accept") String accept, int sid,
			int cid, HttpSession session) {
		
		ActivityCompletion activityCompletion = new ActivityCompletion();

		if (session != null && session.getAttribute("facultyId") != null) {

			int responseCode;
			MediaType mediaType = MediaType.APPLICATION_XML_TYPE;
			Client client = getClient();

			/* if json requested */
			if (accept.equals("application/json")) {
				client.register(JacksonFeature.class);
				mediaType = MediaType.APPLICATION_JSON_TYPE;
			}

			WebTarget target = client.target(STUDENT_SERVICES_URL + sid + "/course/" + cid);
			Invocation getAddrEntryInvocation = target.request(mediaType).buildGet();
			Response response = getAddrEntryInvocation.invoke();

			responseCode = response.getStatus();
			logger.info("/facultyDetailsController/search: The response code is: " + responseCode);

			if (responseCode == 200) {
				activityCompletion = response.readEntity(ActivityCompletion.class);
			}

		}

		return activityCompletion;
	}
	
	@RequestMapping(value = "/facultyDetailsController/update", method = RequestMethod.POST, consumes={"application/json"})
	@ResponseBody
	public String updateStudentActivity(@RequestBody Activity activity, HttpSession session) {
		
		if (session != null && session.getAttribute("facultyId") != null) {
			
			int facultyId = (Integer) session.getAttribute("facultyId");
		
			int updated = facultyService.updateActivity(activity, facultyId);
			
			if(updated == 1) {
				return "success";
			}
			
		}
		
		
		return "error";
	}
	

	private static Client getClient() {
		if (client == null) {
			client = ClientBuilder.newClient();
		}

		return client;
	}

}
