package com.project.batacademy.dao.jdbc;

import java.util.List;

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

	public boolean authenticateFaculty(int userId, String pwd) throws Exception {

		try {
			String sql = "select facultyId, firstName, lastname, gender, phone, designation, enable from faculty where facultyId=:facultyId and password =:password";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("facultyId", userId);
			params.addValue("password", pwd);
			Faculty faculty = dbTemplate.queryForObject(sql, params, facultyRowMapper);
			return true;
		} catch (Exception e) {
			logger.error("FacultyDaoJdbcImpl getFacultyDetails by id and pwd: " + e.getMessage());
			throw e;
		}

	}

	public Faculty getFacultyDetails(int facultyId) throws Exception {
		Faculty faculty = null;
		try {
			String sql = "select facultyId, firstName, lastname, gender, phone, designation, enable from faculty where facultyId=:facultyId";
			MapSqlParameterSource params = new MapSqlParameterSource("facultyId", facultyId);
			faculty = dbTemplate.queryForObject(sql, params, facultyRowMapper);
		} catch (Exception e) {
			logger.error("FacultyDaoJdbcImpl getFacultyDetails by id: " + e);
			throw e;
		}

		return faculty;

	}

	@Override
	public Faculty getFacultyWithPassword(int facultyId) throws Exception {
		Faculty faculty = null;
		try {
			String sql = "select * from faculty where facultyId=:facultyId";
			MapSqlParameterSource params = new MapSqlParameterSource("facultyId", facultyId);
			faculty = dbTemplate.queryForObject(sql, params, facultyRowMapper);
		} catch (Exception e) {
			logger.error("FacultyDaoJdbcImpl getFacultyWithPassword by id: " + e);
			throw e;
		}

		return faculty;
	}

	@Override
	public void updateEnableColumn(boolean enabled) throws Exception {
		try {
			String sql = "update faculty set enable=:enabled where facultyId=1";
			MapSqlParameterSource params = new MapSqlParameterSource("enabled", enabled);
			dbTemplate.update(sql,params);
			
		} catch(Exception e) {
			logger.error("FacultyDaoJdbcImpl updateEnableColumn: " + e.getMessage());
			throw e;
		}		
	}

	@Override
	public boolean isRegisterationEnabled() throws Exception {
		
		try {
			String sql = "select enable from faculty where facultyId=1";
			return jdbcTemplate.queryForObject(sql, Boolean.class);
		} catch (Exception e) {
			logger.error("FacultyDaoJdbcImpl isRegisterationEnabled" + e);
			throw e;
		}
	}

	@Override
	public List<Faculty> getAllFaculty() throws Exception {
		try {
			String sql = "select * from faculty";
			List<Faculty> facultyList =  jdbcTemplate.query(sql, facultyRowMapper);
			return facultyList;
		} catch (Exception e) {
			logger.error("FacultyDaoJdbcImpl getAllFaculty " + e);
			throw e;
		}
	}

	@Override
	public String getFacultyNameForAGivenCourseID(int courseId) throws Exception {
		try {
			String sql = "select F.firstName from course C, faculty F WHERE C.facultyId = F.facultyId and C.courseId=:courseId";
			MapSqlParameterSource params = new MapSqlParameterSource("courseId", courseId);
			String facultyName =  dbTemplate.queryForObject(sql, params, String.class);
			return facultyName;
		} catch (Exception e) {
			logger.error("FacultyDaoJdbcImpl getFacultyNameForAGivenCourseID " + e);
			throw e;
		}
	}

}
