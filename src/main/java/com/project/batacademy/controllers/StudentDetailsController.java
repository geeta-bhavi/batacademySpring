package com.project.batacademy.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.project.batacademy.domain.Course;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.RegisteredCoursesId;
import com.project.batacademy.domain.RegisteredStudentCourses;
import com.project.batacademy.domain.SelectedCoursesBean;
import com.project.batacademy.domain.Student;
import com.project.batacademy.services.FacultyService;
import com.project.batacademy.services.StudentService;

@Controller
public class StudentDetailsController {

	private static final Logger logger = LoggerFactory.getLogger(StudentDetailsController.class);

	@Autowired
	@Qualifier("studentServiceImpl")
	StudentService studentService;

	@Autowired
	@Qualifier("facultyServiceImpl")
	FacultyService facultyService;

	@RequestMapping(value = "/studentDetailsController", method = RequestMethod.GET)
	public ModelAndView getStudentDetails(HttpSession session) {

		ModelAndView modelView = new ModelAndView("home");

		if (session != null) {

			if (session.getAttribute("studentId") != null) {

				int studentId = (Integer) session.getAttribute("studentId");
				Student student = null;

				try {
					List<Course> courses = new ArrayList<Course>();
					List<SelectedCoursesBean> selectedCourses = new ArrayList<SelectedCoursesBean>();
					Map<Integer, String> facultyMap = new HashMap();
					modelView = new ModelAndView("studentDetails");

					student = studentService.getStudentDetails(studentId);

					boolean isStudentRegistered = student.isRegistered();
					boolean isRegistrationEnabled = facultyService.isRegisterationEnabled();

					modelView.addObject("student", student);
					modelView.addObject("isRegistrationEnabled", isRegistrationEnabled);
					modelView.addObject("isStudentRegistered", isStudentRegistered);

					if (isRegistrationEnabled && !isStudentRegistered) {
						courses = studentService.getRemainingCourses(studentId);
						facultyMap = facultyService.getFacultyName();

						modelView.addObject("courses", courses);
						modelView.addObject("faculty", facultyMap);
					} else {
						selectedCourses = studentService.getRegisteredCourses(studentId);
						logger.info("student selectedcourses: " + selectedCourses);
						modelView.addObject("selectedCourses", selectedCourses);
					}

				} catch (Exception e) {
					modelView = new ModelAndView("handleError");
					modelView.addObject("message", e.getMessage());
				}

			}
		}

		return modelView;
	}

	@RequestMapping(value = "/studentDetailsController/register", method = RequestMethod.POST, consumes={"application/json", "application/xml"})
	@ResponseBody
	public String handleRegisterationOfcourses(@RequestBody RegisteredStudentCourses courses, HttpSession session) {
		logger.info("courses selected:"+courses.getCourseList().size());
		
		try {
			int studentId = courses.getStudentId();
	        List<Map<?,?>> list = courses.getCourseList();
	        int listSize = list.size();
	        List<RegisteredCourses> registeredCoursesList = new ArrayList<RegisteredCourses>();
	        RegisteredCourses registeredCourses = new RegisteredCourses();
	        
	        for (int i = 0; i < listSize; i++) {
	        	registeredCourses = new RegisteredCourses();
	        	RegisteredCoursesId registeredCoursesId = new RegisteredCoursesId();
	
	            String courseId = (String) list.get(i).get("courseId");
	            String courseName = (String) list.get(i).get("coursename");
	            logger.info("courseId " + courseId);
	            logger.info("courseName " + courseName);
	
	            registeredCoursesId.setCourseId(Integer.parseInt(courseId));
	            registeredCoursesId.setStudentId(studentId);
	
	            registeredCourses.setId(registeredCoursesId);
	            registeredCourses.setCourseName(courseName);
	
	            registeredCoursesList.add(registeredCourses);
	            
	        }
	        
	        studentService.updateRegisteredCourses(registeredCoursesList);
            studentService.setRegisteredTrue(studentId);
            
            return "success";
            
		} catch(Exception e) {
			
		}
		
		return "";
		
	}
	
	
	
	
	
	
	
	
}
