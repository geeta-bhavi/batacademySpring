package com.project.batacademy.controllers;

import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.batacademy.domain.Student;
import com.project.batacademy.services.FacultyService;
import com.project.batacademy.services.StudentService;

@Controller
public class StudentDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(StudentDetailsController.class);

	@Autowired
	@Qualifier("studentServiceImpl")
	StudentService studentService;

	@RequestMapping(value = "/studentDetailsController", method = RequestMethod.GET)
	public ModelAndView getStudentDetails(HttpSession session) {

		ModelAndView modelView = new ModelAndView("home");

		if (session != null) {

			if (session.getAttribute("studentId") != null) {

				int studentId = (Integer) session.getAttribute("studentId");
				Student student = null;

				try {
					student = studentService.getStudentDetails(studentId);
					modelView = new ModelAndView("studentDetails");
					modelView.addObject("student", student);
				} catch (Exception e) {
					modelView = new ModelAndView("handleError");
					modelView.addObject("message", e.getMessage());
				}

			}
		}

		return modelView;

	}
}
