package com.daekyo.quiz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLiteManager {
	
	private static final Logger logger = LoggerFactory.getLogger(SQLiteManager.class);

	String path = getClass().getResource("/data/quiz.db").toString();
	// 상수 설정
	// - Database 변수
	private static final String SQLITE_JDBC_DRIVER = "org.sqlite.JDBC";
	private static final String SQLITE_MEMORY_DB_URL = "jdbc:sqlite::memory";

	// - Database 옵션 변수
	private static final boolean OPT_AUTO_COMMIT = true;
	private static final int OPT_VALID_TIMEOUT = 500;

	// 변수 설정
	// - Database 접속 정보 변수
	private Connection conn = null;
	private String driver = null;
	private String url = null;

	// 생성자
	public SQLiteManager() {
		// JDBC Driver 설정
		this.driver = SQLITE_JDBC_DRIVER;
		this.url = "jdbc:sqlite:" + getClass().getResource("/data/quiz.db");
		//this.url = "jdbc:sqlite:C:/quiz.db";
	}

	// DB 연결 함수
	public Connection createConnection() {
		try {
			// JDBC Driver Class 로드
			Class.forName(this.driver);

			// DB 연결 객체 생성
			this.conn = DriverManager.getConnection(this.url);

			// 옵션 설정
			// - 자동 커밋
			this.conn.setAutoCommit(OPT_AUTO_COMMIT);

		} catch (Exception e) {
			logger.info(e + "");
		}

		return this.conn;
	}

	// DB 연결 종료 함수
	public void closeConnection() {
		try {
			if (this.conn != null) {
				this.conn.close();
			}
		} catch (SQLException e) {
			logger.info(e + "");
		} finally {
			this.conn = null;
		}
	}

	// DB 재연결 함수
	public Connection ensureConnection() {
		try {
			if (this.conn == null || this.conn.isValid(OPT_VALID_TIMEOUT)) {
				closeConnection(); // 연결 종료
				createConnection(); // 연결
			}
		} catch (SQLException e) {
			logger.info(e + "");
		}
		return this.conn;
	}
	
	// DB 커밋
	public void commit() {
		try {
			this.conn.commit();
		} catch (SQLException e) {
			logger.info(e + "");
		}
	}

	// DB 연결 객체 가져오기
	public Connection getConnection() {
		return this.conn;
	}

}
