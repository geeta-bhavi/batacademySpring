package com.project.batacademy.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.project.batacademy.dao.ActivityDao;
import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.RegisteredCoursesId;

@Service("activityServiceImpl")
public class ActivityServiceImpl implements ActivityService {

	@Autowired
	@Qualifier("activityDaoJdbc")
	private ActivityDao activityDao;

	@Override
	public Activity getActivityDetails(int studentId, int courseId) throws Exception {
		Activity activity = (Activity) activityDao.getActivityDetails(studentId, courseId);
		return activity;
	}

	@Override
	public int updateActivityScores(Activity activity, int facultyId) throws Exception {
		return activityDao.updateActivityScores(activity, facultyId);
	}

	@Override
	public int insertActivity(Activity activity, int facultyId) throws Exception {
		return activityDao.insertActivity(activity, facultyId);
	}

	@Override
	public List<Activity> getActivitiesOfNotCompletedCourses(List<RegisteredCoursesId> notCompletedCourses)
			throws Exception {
		return activityDao.getActivitiesOfNotCompletedCourses(notCompletedCourses);
	}

	@Override
	public Activity getActivityforGiveCouseAndStudent(int courseId, int studentId) throws Exception {
		return activityDao.getActivityforGiveCouseAndStudent(courseId, studentId);
	}

}
