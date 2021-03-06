package com.project.batacademy.dao.jdbc;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.batacademy.dao.RegisteredCoursesDao;
import com.project.batacademy.domain.RegisteredCourses;
import com.project.batacademy.domain.RegisteredCoursesId;

@Repository("registeredCoursesDaoJdbc")
public class RegisteredCoursesDaoImpl implements RegisteredCoursesDao {

	private static final Logger logger = LoggerFactory.getLogger(RegisteredCoursesDaoImpl.class);
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private RegisteredCoursesRowMapper registeredCoursesRowMapper;
	private RegisteredCoursesIdRowMapper registeredCoursesIdRowMapper;

	@PostConstruct
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dbTemplate = new NamedParameterJdbcTemplate(dataSource);
		registeredCoursesRowMapper = new RegisteredCoursesRowMapper();
		registeredCoursesIdRowMapper = new RegisteredCoursesIdRowMapper();
		jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("registeredCourses");
	}

	@Override
	public RegisteredCourses getRegisteredCoursesOfStudent(int studentId, int courseId) throws Exception {
		try {
			String sql = "select * from registeredCourses where studentId=:studentId and courseId =:courseId";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("studentId", studentId);
			params.addValue("courseId", courseId);
			RegisteredCourses regCourses = dbTemplate.queryForObject(sql, params, registeredCoursesRowMapper);
			return regCourses;
		} catch (Exception e) {
			logger.error(
					"RegisteredCoursesDaoImpl getRegisteredCoursesOfStudent by studentId and courseId: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<RegisteredCoursesId> getNotCompletedCoursesOfStudents() throws Exception {
		try {
			String sql = "select * from registeredCourses where completed=false";
			List<RegisteredCoursesId> listOfRegCoursesId = jdbcTemplate.query(sql, registeredCoursesIdRowMapper);
			return listOfRegCoursesId;
		} catch (Exception e) {
			logger.error(
					"RegisteredCoursesDaoImpl getNotCompletedCoursesOfStudents: " + e.getMessage());
			throw e;
		}

	}

	@Override
	public void updateCompletedColumn(boolean completed) throws Exception {
		try {
			String sql = "update registeredCourses set completed=:completed";
			MapSqlParameterSource params = new MapSqlParameterSource("completed", completed);		
			dbTemplate.update(sql,params);
			
		} catch(Exception e) {
			logger.error("RegisteredCoursesDaoImpl updateCompletedColumn: " + e.getMessage());
			throw e;
		}
		
	}

	@Override
	public List<Integer> getCoursesIdGivenStudentId(int studentId) throws Exception {
		try {
			String sql = "select courseId from registeredCourses where studentId=:studentId";
			MapSqlParameterSource params = new MapSqlParameterSource("studentId", studentId);
			List<Integer> listOfRegCoursesId = dbTemplate.queryForList(sql, params, Integer.class);
			return listOfRegCoursesId;
		} catch (Exception e) {
			logger.error(
					"RegisteredCoursesDaoImpl getCoursesIdGivenStudentId: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public List<RegisteredCourses> getRegisteredCoursesForStudent(int studentId) throws Exception {
		try {
			String sql = "select * from registeredCourses where studentId=:studentId";
			MapSqlParameterSource params = new MapSqlParameterSource("studentId", studentId);
			List<RegisteredCourses> listOfRegCourses = dbTemplate.query(sql, params, registeredCoursesRowMapper);
			return listOfRegCourses;
		} catch (Exception e) {
			logger.error(
					"RegisteredCoursesDaoImpl getRegisteredCoursesForStudent: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public void updateRegisteredCourses(List<RegisteredCourses> registeredCoursesList) throws Exception {
		try {
            for (RegisteredCourses selectedcourse : registeredCoursesList) {
            	MapSqlParameterSource params = new MapSqlParameterSource();
            	logger.error("dao registeredcourse: "+selectedcourse.getId().getCourseId());
				params.addValue("courseId", selectedcourse.getId().getCourseId());
				params.addValue("courseName", selectedcourse.getCourseName());
				params.addValue("studentId", selectedcourse.getId().getStudentId());
				params.addValue("completed", false);
                jdbcInsert.execute(params);
            }
		} catch(Exception e) {
			logger.error(
					"RegisteredCoursesDaoImpl updateRegisteredCourses: " + e.getMessage());
			throw e;
		}
		
	}

}
