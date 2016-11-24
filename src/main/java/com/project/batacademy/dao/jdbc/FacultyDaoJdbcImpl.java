package com.project.batacademy.dao.jdbc;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.batacademy.dao.FacultyDao;
import com.project.batacademy.domain.Faculty;

@Repository("facultyDaoJdbc")
@Transactional
public class FacultyDaoJdbcImpl implements FacultyDao {
	private static final Logger logger = LoggerFactory.getLogger(FacultyDaoJdbcImpl.class);
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private FacultyRowMapper facultyRowMapper;

	@PostConstruct
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dbTemplate = new NamedParameterJdbcTemplate(dataSource);
		facultyRowMapper = new FacultyRowMapper();
		jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("student").usingGeneratedKeyColumns("studentId");
	}

	public Faculty getFacultyDetails(int userId, String pwd) {

		Faculty faculty = null;
		try {
			String sql = "select * from faculty where facultyId=:facultyId and password =:password";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("facultyId", userId);
			params.addValue("password", pwd);
			faculty = dbTemplate.queryForObject(sql, params, facultyRowMapper);
		} catch (EmptyResultDataAccessException e) {
			logger.error("FacultyDaoJdbcImpl getFacultyDetails by id and pwd: " + e);
		}

		return faculty;
	}

	public Faculty getFacultyDetails(int facultyId) {
		Faculty faculty = null;
		try {
			String sql = "select * from faculty where facultyId=:facultyId";
			MapSqlParameterSource params = new MapSqlParameterSource("facultyId", facultyId);
			faculty = dbTemplate.queryForObject(sql, params, facultyRowMapper);
		} catch (EmptyResultDataAccessException e) {
			logger.error("FacultyDaoJdbcImpl getFacultyDetails by id: " + e);
		}

		return faculty;

	}

}
