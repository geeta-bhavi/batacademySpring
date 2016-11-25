package com.project.batacademy.dao.jdbc;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.batacademy.dao.ActivityDao;
import com.project.batacademy.domain.Activity;

@Repository("activityDaoJdbc")
@Transactional
public class ActivityDaoJdbcImpl implements ActivityDao {

	private static final Logger logger = LoggerFactory.getLogger(CourseDaoJdbcImpl.class);
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
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("studentId", studentId);
			params.addValue("courseId", courseId);
			Activity activity = dbTemplate.queryForObject(sql, params, activityRowMapper);
			return activity;
		} catch (Exception e) {
			logger.error("ActivityDaoJdbcImpl getActivityDetails by student id and course id: " + e.getMessage());
			throw e;
		}
	}

	/*
	 * update activity table only when course is not completed and is taught by
	 * the same faculty who teaches the course
	 */
	@Override
	public int updateActivity(Activity activity, int facultyId) throws Exception {
		try {
			String sql = "update activity A "
					+ "INNER JOIN registeredCourses B ON A.courseId = B.courseId and A.studentId = B.studentId "
					+ "INNER JOIN course C ON B.courseId = C.courseId " + "set a1=:a1, a2=:a2, a3=:a3 "
					+ "where A.studentId=:studentId and A.courseId=:courseId and C.facultyId =:facultyId and B.completed = false";
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
			logger.error("ActivityDaoJdbcImpl updateActivity by student id and course id: " + e.getMessage());
			throw e;
		}
	}

}
