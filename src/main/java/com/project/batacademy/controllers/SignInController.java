package com.project.batacademy.controllers;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.batacademy.services.AuthenticateService;
import com.project.batacademy.services.AuthenticateServiceImpl;

@Controller
public class SignInController {
	private static final Logger logger = LoggerFactory.getLogger(SignInController.class);
	@Autowired
	@Qualifier("authServiceImpl")
	private AuthenticateService auth;
	
	
	@RequestMapping(value = "/signin", method = RequestMethod.POST)
	@ResponseBody
	public String signIn(String id, String password, String userType, HttpSession session) {
		
		String userExists = "";
		int userId = 0;
		

        if (id.length() != 0 && password.length() != 0) {
            userId = Integer.parseInt(id);
            
            if (userType.equalsIgnoreCase("student")) {
                userExists = auth.checkIfStudentExists(userId, password);
            } else {
                userExists = auth.checkIfFacultyExists(userId, password);
            }
        }
        
        logger.info("user exists: "+userExists);
        
        if (userExists.equalsIgnoreCase("student")) {
            if (session != null) {
            	/*if session exists, make other attribute as null */
                session.setAttribute("facultyId", null);         
            }
            session.setAttribute("studentId", userId);
            return "student";
        } else if (userExists.equalsIgnoreCase("faculty")) {
        	if (session != null) {
            	/*if session exists, make other attribute as null */
                session.setAttribute("studentId", null);         
            }
            session.setAttribute("facultyId", userId);
            return "faculty";
        } else {
        
        	return "error";
        }
		
	}
	
	@RequestMapping(value = "/signout", method = RequestMethod.POST)
	@ResponseBody
	public String signOut(HttpSession session) {
		if(session != null) {
            session.invalidate();
        }
		
		return "success";
	}

}
