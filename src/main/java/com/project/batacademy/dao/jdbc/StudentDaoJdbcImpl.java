package com.project.batacademy.dao.jdbc;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.project.batacademy.dao.StudentDao;
import com.project.batacademy.domain.Student;

@Repository("studentDaoJdbc")
public class StudentDaoJdbcImpl implements StudentDao {
	private static final Logger logger = LoggerFactory.getLogger(StudentDaoJdbcImpl.class);
	@Autowired
	@Qualifier("dataSource")
	private DataSource dataSource;
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate dbTemplate;
	private SimpleJdbcInsert jdbcInsert;
	private StudentRowMapper studentRowMapper;

	@PostConstruct
	public void setup() {
		jdbcTemplate = new JdbcTemplate(dataSource);
		dbTemplate = new NamedParameterJdbcTemplate(dataSource);
		studentRowMapper = new StudentRowMapper();
		jdbcInsert = new SimpleJdbcInsert(dataSource).withTableName("student").usingGeneratedKeyColumns("studentId");
	}

	public boolean authenticateStudent(int userId, String pwd) throws Exception {
		try {
			String sql = "select studentId, firstName, lastName, gender, phone, cgpa, registered from student where studentId=:studentId and password =:password";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("studentId", userId);
			params.addValue("password", pwd);
			Student stud = dbTemplate.queryForObject(sql, params, studentRowMapper);
			return true;
		} catch (Exception e) {
			logger.error("StudentDaoJdbcImpl getStudentDetails by id and pwd: " + e.getMessage());
			throw e;
		}
	}

	public Student getStudentDetails(int studentId) throws Exception {
		Student stud = null;
		try {
			String sql = "select studentId, firstName, lastName, gender, phone, cgpa, registered from student where studentId=:studentId";
			MapSqlParameterSource params = new MapSqlParameterSource("studentId", studentId);
			stud = dbTemplate.queryForObject(sql, params, studentRowMapper);
			return stud;
		} catch (Exception e) {
			logger.error("StudentDaoJdbcImpl getStudentDetails by id: " + e.getMessage());
			throw e;
		}

	}

	@Override
	public int removeStudentWithId(int studentId) throws Exception {
		String sql = "delete from student where studentId=:studentId";
		MapSqlParameterSource params = new MapSqlParameterSource("studentId", studentId);
		int result = dbTemplate.update(sql, params);
		return result;
	}

	@Override
	public Student getStudentWithPassword(int studentId) throws Exception {
		Student stud = null;
		try {
			String sql = "select * from student where studentId=:studentId";
			MapSqlParameterSource params = new MapSqlParameterSource("studentId", studentId);
			stud = dbTemplate.queryForObject(sql, params, studentRowMapper);
			return stud;
		} catch (Exception e) {
			logger.error("StudentDaoJdbcImpl getStudentWithPassword by id: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public float getStudentCGPA(int studentId) throws Exception {
		try {

			String sql = "select cgpa from Student where studentId=:studentId";
			MapSqlParameterSource params = new MapSqlParameterSource("studentId", studentId);

			return dbTemplate.queryForObject(sql, params, Float.class);

		} catch (Exception e) {
			logger.error("StudentDaoJdbcImpl getStudentCGPA by id: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public void updateStudentCGPA(int studentId, float cgpa) throws Exception {
		try {
			String sql = "update student set cgpa=:cgpa where studentId=:studentId";
			MapSqlParameterSource params = new MapSqlParameterSource();
			params.addValue("studentId", studentId);
			params.addValue("cgpa", cgpa);
			
			dbTemplate.update(sql,params);
			
		} catch(Exception e) {
			logger.error("StudentDaoJdbcImpl updateStudentCGPA: " + e.getMessage());
			throw e;
		}

	}

	@Override
	public void updateRegisteredColumn(boolean registered) throws Exception {
		try {
			String sql = "update student set registered=:registered";
			MapSqlParameterSource params = new MapSqlParameterSource("registered", registered);
			dbTemplate.update(sql,params);
			
		} catch(Exception e) {
			logger.error("StudentDaoJdbcImpl updateRegisteredColumn: " + e.getMessage());
			throw e;
		}
		
	}

	@Override
	public int addStudent(Student student) throws Exception {
		try {
			BeanPropertySqlParameterSource params = new BeanPropertySqlParameterSource(student);
			Number id = jdbcInsert.executeAndReturnKey(params);
			int studentId = id.intValue();
			return studentId;
			
		} catch(Exception e) {
			logger.error("StudentDaoJdbcImpl addStudent: " + e.getMessage());
			throw e;
		}
	}

	@Override
	public void setRegisteredTrue(int studentId) throws Exception {
		try {
			String sql = "update student set registered=true where studentId=:studentId";
			MapSqlParameterSource params = new MapSqlParameterSource("studentId", studentId);
			dbTemplate.update(sql,params);
			
		} catch(Exception e) {
			logger.error("StudentDaoJdbcImpl setRegisteredTrue: " + e.getMessage());
			throw e;
		}
		
	}

}
