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

import com.project.batacademy.domain.Faculty;

@Controller
public class FacultyDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(FacultyDetailsController.class);
	private static String FACULTY_SERVICES_URL = "http://localhost:8080/batacademy/webservices/facultyrest/faculty/";
	private static Client client = null;

	@RequestMapping(value = "/facultyDetailsController", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView facultyDetails(HttpSession session) {

		ModelAndView modelView = new ModelAndView("home");

		if (session != null) {

			if (session.getAttribute("facultyId") != null) {
				int idToLookup = (Integer) session.getAttribute("facultyId");
				int responseCode;
				Faculty faculty = null;

				Client client = getClient();
				WebTarget target = client.target(FACULTY_SERVICES_URL + idToLookup);
				
				Invocation getAddrEntryInvocation = target.request(MediaType.APPLICATION_XML_TYPE).buildGet();
				Response response = getAddrEntryInvocation.invoke();

				responseCode = response.getStatus();
				logger.info("The response code is: " + responseCode);
				if (responseCode == 200) {
					faculty = response.readEntity(Faculty.class);
					/* if president signed in, then go to president details page */
	                if (idToLookup == 1) {
	                	modelView = new ModelAndView("presidentDetails");
	                } else {
	                	modelView = new ModelAndView("facultyDetails");
	                }
					modelView.addObject("faculty", faculty);
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
