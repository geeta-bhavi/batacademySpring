package com.project.batacademy.dao.jdbc;

import java.util.ArrayList;
import java.util.List;

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

import com.project.batacademy.dao.CourseDao;
import com.project.batacademy.domain.Course;

@Repository("courseDaoJdbc")
public class CourseDaoJdbcImpl implements CourseDao {

	private static final Logger logger = LoggerFactory.getLogger(CourseDaoJdbcImpl.class);
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private CourseRowMapper courseRowMapper;

	@PostConstruct
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dbTemplate = new NamedParameterJdbcTemplate(dataSource);
		courseRowMapper = new CourseRowMapper();
		jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("course");
	}

	public List<Course> getCoursesTaughtByFaculty(int facultyId) throws Exception {
		try {
			String sql = "SELECT * FROM course WHERE facultyId = :facultyId";
			MapSqlParameterSource params = new MapSqlParameterSource("facultyId", facultyId);
			List<Course> matchingCourses = dbTemplate.query(sql, params, courseRowMapper);
			return matchingCourses;
		} catch (Exception e) {
			logger.error("CourseDaoJdbcImpl getCoursesTaughtByFaculty by id: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<Course> getRemainingCourses(List<Integer> coursesTaken) throws Exception {
		try {
			String sql = "SELECT * FROM course WHERE courseId NOT IN (:coursesTaken)";
			MapSqlParameterSource params = new MapSqlParameterSource("coursesTaken", coursesTaken);
			List<Course> remainingCourses = dbTemplate.query(sql, params, courseRowMapper);
			return remainingCourses;
		} catch (Exception e) {
			logger.error("CourseDaoJdbcImpl getRemainingCourses: " + e.getMessage());
			throw e;
		}
	}

}
