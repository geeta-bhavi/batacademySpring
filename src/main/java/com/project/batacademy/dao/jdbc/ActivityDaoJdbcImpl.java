package com.project.batacademy.dao.jdbc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.batacademy.dao.ActivityDao;
import com.project.batacademy.domain.Activity;
import com.project.batacademy.domain.RegisteredCoursesId;

@Repository("activityDaoJdbc")
public class ActivityDaoJdbcImpl implements ActivityDao {

	private static final Logger logger = LoggerFactory.getLogger(ActivityDaoJdbcImpl.class);
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private ActivityRowMapper activityRowMapper;

	@PostConstruct
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dbTemplate = new NamedParameterJdbcTemplate(dataSource);
		activityRowMapper = new ActivityRowMapper();
		jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("activity");
	}

	@Override
	public Activity getActivityDetails(int studentId, int courseId) throws Exception {
		try {
			String sql = "select * from activity where studentId=:studentId and courseId=:courseId";
			Map<String, Integer> params = new HashMap<String, Integer>();
			params.put("studentId", studentId);
			params.put("courseId", courseId);
			SqlParameterSource paramSource = new MapSqlParameterSource(params);
			List<Activity> activity = dbTemplate.query(sql, paramSource, activityRowMapper);
			if (activity != null && !activity.isEmpty()) {
				return activity.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error("ActivityDaoJdbcImpl getActivityDetails by student id and course id: " + e.getMessage());
			throw e;
		}
	}

	/*
	 * update activity table only when course is not completed and the faculty
	 * id is same faculty who teaches the course
	 */
	@Override
	public int updateActivityScores(Activity activity, int facultyId) throws Exception {

		try {
			String sql = "update activity A "
					+ "INNER JOIN registeredCourses R ON A.courseId = R.courseId and A.studentId = R.studentId "
					+ "INNER JOIN course C ON R.courseId = C.courseId " + "set a1=:a1, a2=:a2, a3=:a3 "
					+ "where A.studentId=:studentId and A.courseId=:courseId and C.facultyId =:facultyId and R.completed = false";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("a1", activity.getA1());
			params.addValue("a2", activity.getA2());
			params.addValue("a3", activity.getA3());
			params.addValue("studentId", activity.getId().getStudentId());
			params.addValue("courseId", activity.getId().getCourseId());
			params.addValue("facultyId", facultyId);
			int noOfRowsUpdate = dbTemplate.update(sql, params);
			return noOfRowsUpdate;

		} catch (Exception e) {
			logger.error("ActivityDaoJdbcImpl updateActivityScores " + e.getMessage());
			throw e;
		}

	}

	/*
	 * insert into activity table only when course is not completed and the
	 * faculty id is same faculty who teaches the course
	 */

	public int insertActivity(Activity activity, int facultyId) throws Exception {

		int studentId = activity.getId().getStudentId();
		int courseId = activity.getId().getCourseId();
		try {

			String sql = "select * from registeredCourses R INNER JOIN course C ON R.courseId = C.courseId "
					+ "where R.studentId=:studentId and R.courseId=:courseId and C.facultyId =:facultyId and R.completed = false";
			Map<String, Integer> paramsSource = new HashMap<String, Integer>();
			paramsSource.put("studentId", studentId);
			paramsSource.put("courseId", courseId);
			paramsSource.put("facultyId", facultyId);
			List<Map<String, Object>> registeredCourses = dbTemplate.queryForList(sql, paramsSource);
			if (registeredCourses != null && !registeredCourses.isEmpty()) {

				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("studentId", studentId);
				params.addValue("courseId", courseId);
				params.addValue("a1", activity.getA1());
				params.addValue("a2", activity.getA2());
				params.addValue("a3", activity.getA3());
				jdbcInsert.execute(params);
				return 1;
			}
			return 0;

		} catch (Exception e) {
			logger.error("ActivityDaoJdbcImpl insertActivity " + e.getMessage());
			throw e;
		}

	}

	@Override
	public List<Activity> getActivitiesOfNotCompletedCourses(List<RegisteredCoursesId> notCompletedCourses)
			throws Exception {
		List<Activity> activities = new ArrayList<Activity>();
		try{
			
			for (RegisteredCoursesId regCourse : notCompletedCourses) {
				String sql = "Select * from activity where studentId=:studentId and courseId=:courseId";
				MapSqlParameterSource params = new MapSqlParameterSource();
				params.addValue("studentId", regCourse.getStudentId());
				params.addValue("courseId", regCourse.getCourseId());
				Activity activity = (Activity) dbTemplate.queryForObject(sql, params, activityRowMapper);
				activities.add(activity);

			}
			
		} catch (Exception e) {
			logger.error("ActivityDaoJdbcImpl getActivitiesOfNotCompletedCourses " + e.getMessage());
			throw e;
		}
		
		return activities;
	}

}
