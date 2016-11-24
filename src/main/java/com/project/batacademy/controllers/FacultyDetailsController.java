package com.project.batacademy.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.services.FacultyService;

@Controller
public class FacultyDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(FacultyDetailsController.class);

	@Autowired
	@Qualifier("facultyServiceImpl")
	FacultyService facultyService;

	@RequestMapping(value = "/facultyDetailsController", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView facultyDetails(HttpSession session) {

		ModelAndView modelView = new ModelAndView("home");

		if (session != null) {

			if (session.getAttribute("facultyId") != null) {
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
		}

		return modelView;

	}

}
