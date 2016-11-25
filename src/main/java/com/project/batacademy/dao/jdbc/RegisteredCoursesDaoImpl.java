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

import com.project.batacademy.dao.RegisteredCoursesDao;
import com.project.batacademy.domain.RegisteredCourses;

@Repository("registeredCoursesDaoJdbc")
@Transactional
public class RegisteredCoursesDaoImpl implements RegisteredCoursesDao {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisteredCoursesDaoImpl.class);
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private RegisteredCoursesRowMapper registeredCoursesRowMapper;

	@PostConstruct
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dbTemplate = new NamedParameterJdbcTemplate(dataSource);
		registeredCoursesRowMapper = new RegisteredCoursesRowMapper();
		jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("registeredCourses");
	}

	@Override
	public RegisteredCourses registeredCourseOfStudent(int studentId, int courseId) throws Exception {
		try {
			String sql = "select * from registeredCourses where studentId=:studentId and courseId =:courseId";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("studentId", studentId);
			params.addValue("courseId", courseId);
			RegisteredCourses regCourses =  dbTemplate.queryForObject(sql, params, registeredCoursesRowMapper);
			return regCourses;
		} catch(Exception e) {
			logger.error("RegisteredCoursesDaoImpl registeredCourseOfStudent by studentId and courseId: "+ e.getMessage());
			throw e;
		}
	}

}
