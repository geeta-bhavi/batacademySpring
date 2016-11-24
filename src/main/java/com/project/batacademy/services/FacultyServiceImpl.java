package com.project.batacademy.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.FacultyDao;
import com.project.batacademy.domain.Faculty;
import com.project.batacademy.domain.Student;

@Service("facultyServiceImpl")
public class FacultyServiceImpl implements FacultyService {

	@Autowired
	@Qualifier("facultyDaoJdbc")
	private FacultyDao facultyDao;

	@Override
	public Faculty getFacultyDetails(int facultyId) {
		try {
			Faculty faculty = (Faculty) facultyDao.getFacultyDetails(facultyId);
			return faculty;
		} catch (Exception e) {
			return null;
		}
	}

}
