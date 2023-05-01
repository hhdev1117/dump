package com.daekyo.quiz.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SQLiteManagerToUser {
	
	private static final Logger logger = LoggerFactory.getLogger(SQLiteManagerToUser.class);

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
	public SQLiteManagerToUser() {
		String userdb_filepath = "";
		try {
			// 파일 있는지 확인
			userdb_filepath = (this.getClass().getClassLoader().getResource("/data/user_temp.db").getPath()).replace("quiz/WEB-INF/classes/data/user_temp.db", "user/user.db") + "";
			
			
			if(new File(userdb_filepath).exists()) {

			} else {
				logger.info("==============================================================");
				logger.info("	사용자정보가 없습니다. USER DB를 생성합니다. ");
				logger.info("==============================================================");
				// 2. 복사        
				FileInputStream infile = new FileInputStream(this.getClass().getClassLoader().getResource("/data/user_temp.db").getPath());
			    FileOutputStream outfile = new FileOutputStream((this.getClass().getClassLoader().getResource("/data/user_temp.db").getPath()).replace("quiz/WEB-INF/classes/data/user_temp.db", "user/user.db") + "");
			    
		        byte[] b = new byte[1024];
		        int len = 0;
		        while((len = infile.read(b, 0, 1024)) > 0) {
		        	outfile.write(b, 0, len);
		        }
		        
		        infile.close();
		        outfile.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			// JDBC Driver 설정
			this.driver = SQLITE_JDBC_DRIVER;
			this.url = "jdbc:sqlite:" + userdb_filepath;
		}
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
