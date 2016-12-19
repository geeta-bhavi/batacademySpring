package com.project.batacademy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.RegisteredCoursesDao;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.RegisteredCoursesId;

@Service("registeredCoursesServiceImpl")
public class RegisteredCoursesServiceImpl implements RegisteredCoursesService {

	@Autowired
	@Qualifier("registeredCoursesDaoJdbc")
	private RegisteredCoursesDao registeredCoursesDao;

	@Override
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId) throws Exception {
		RegisteredCourses regCourses = registeredCoursesDao.getRegisteredCoursesOfStudent(studentId, courseId);
		return regCourses;
	}

	@Override
	public List<RegisteredCoursesId> getNotCompletedCoursesOfStudents() throws Exception {
		List<RegisteredCoursesId> regCoursesId = registeredCoursesDao.getNotCompletedCoursesOfStudents();
		return regCoursesId;
	}

	@Override
	public void updateCompletedColumn(boolean completed) throws Exception {
		registeredCoursesDao.updateCompletedColumn(completed);
	}

	@Override
	public List<Integer> getCoursesIdGivenStudentId(int studentId) throws Exception {
		return registeredCoursesDao.getCoursesIdGivenStudentId(studentId);
	}

	@Override
	public List<RegisteredCourses> getRegisteredCoursesForStudent(int studentId) throws Exception {
		return registeredCoursesDao.getRegisteredCoursesForStudent(studentId);
	}

	@Override
	public void updateRegisteredCourses(List<RegisteredCourses> registeredCoursesList) throws Exception {
		registeredCoursesDao.updateRegisteredCourses(registeredCoursesList);
		
	}

}
