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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.batacademy.domain.Student;

@Controller
public class StudentDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(StudentDetailsController.class);
	private static String STUDENT_SERVICES_URL = "http://localhost:8080/batacademy/webservices/studrest/student/";
	private static Client client = null;

	@RequestMapping(value = "/studentDetailsController", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView studentDetails(HttpSession session) {
		
		ModelAndView modelView = new ModelAndView("home");
		
		if (session != null) {

			if (session.getAttribute("studentId") != null) {

				int idToLookup = (Integer) session.getAttribute("studentId");
				int responseCode;
				Student student = null;
								
				Client client = getClient();
				WebTarget target = client.target(STUDENT_SERVICES_URL + idToLookup);

				Invocation getAddrEntryInvocation = target.request(MediaType.APPLICATION_XML_TYPE).buildGet();
				Response response = getAddrEntryInvocation.invoke();

				responseCode = response.getStatus();
				logger.info("The response code is: " + responseCode);
				if (responseCode == 200) {
					student = response.readEntity(Student.class);
					modelView = new ModelAndView("studentDetails");
					modelView.addObject("student", student);
				}
			}
		}

		return modelView;

	}

	private static Client getClient() {
		if (client == null) {
			client = ClientBuilder.newClient();
		}

		return client;
	}
}
