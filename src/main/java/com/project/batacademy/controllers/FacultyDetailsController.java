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
import com.project.batacademy.domain.Student;
import com.project.batacademy.services.FacultyService;
import com.project.batacademy.services.StudentService;

@Controller
public class FacultyDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(FacultyDetailsController.class);

	@Autowired
	@Qualifier("facultyServiceImpl")
	FacultyService facultyService;

	@Autowired
	@Qualifier("studentServiceImpl")
	StudentService studentservice;

	/* get faculty information after authenticating from login page */
	@RequestMapping(value = "/facultyDetailsController", method = RequestMethod.GET)
	public ModelAndView getFacultyDetails(HttpSession session) {

		ModelAndView modelView = new ModelAndView("home");

		if (session != null && session.getAttribute("facultyId") != null) {
			int facultyId = (Integer) session.getAttribute("facultyId");
			Faculty faculty = null;
			List<Course> courses = null;

			faculty = facultyService.getFacultyDetails(facultyId);

			if (faculty != null) {
				courses = facultyService.getCoursesTaughtByFaculty(facultyId);

				/*
				 * if president signed in, then go to president details page
				 */
				if (facultyId == 1) {
					modelView = new ModelAndView("presidentDetails");
				} else {
					modelView = new ModelAndView("facultyDetails");
				}

				modelView.addObject("faculty", faculty);
				modelView.addObject("courses", courses);

			}
		}
		return modelView;
	}

	/*
	 * get student's activity scores and whether the course is completed or not
	 * status
	 */
	@RequestMapping(value = "/facultyDetailsController/searchStudentByCourse", method = RequestMethod.POST)
	public @ResponseBody ActivityCompletion searchStudentByIdAndCourse(int sid, int cid, HttpSession session) {

		ActivityCompletion activityCompletion = new ActivityCompletion();

		if (session != null && session.getAttribute("facultyId") != null) {

			activityCompletion = studentservice.getActivityAndCompletedState(sid, cid);

		}

		return activityCompletion;
	}

	/* update student's activity scores */
	@RequestMapping(value = "/facultyDetailsController/updateActivityScores", method = RequestMethod.POST, consumes = {
			"application/json" })
	@ResponseBody
	public String updateStudentActivityScores(@RequestBody Activity activity, HttpSession session) {

		if (session != null && session.getAttribute("facultyId") != null) {

			int facultyId = (Integer) session.getAttribute("facultyId");

			int updated = facultyService.updateActivityScores(activity, facultyId);

			if (updated == 1) {
				return "success";
			}

		}

		return "error";
	}

	/* President searches a student for deletion */
	@RequestMapping(value = "/facultyDetailsController/presidentSearchStudent", method = RequestMethod.POST)
	public @ResponseBody Student presidentSearchStudent(int sid, HttpSession session) {
		Student student = null;

		if (session != null && session.getAttribute("facultyId") != null) {
			student = studentservice.getStudentDetails(sid);
		}

		return student;
	}

	/* President deletes a student */
	@RequestMapping(value = "/facultyDetailsController/deleteStudent", method = RequestMethod.POST)
	@ResponseBody
	public String presidentDeleteStudent(int sid, HttpSession session) {

		String result = "error";

		if (session != null && session.getAttribute("facultyId") != null) {
			int returnVal = studentservice.removeStudentWithId(sid);
			if (returnVal == 1) {
				result = "success";
			}
		}

		return result;
	}

}
