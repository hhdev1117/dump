package com.daekyo.quiz.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daekyo.quiz.abap.service.impl.AbapMainServiceImpl;
import com.daekyo.quiz.abap.vo.AbapQuizVO;
import com.daekyo.quiz.com.vo.ErrorRateVO;
import com.daekyo.quiz.sqld.vo.Sqld30VO;

public class SQL {
	private static final Logger logger = LoggerFactory.getLogger(AbapMainServiceImpl.class);
	SQLiteManager manager = new SQLiteManager();
	SQLiteManagerToUser user_manager = new SQLiteManagerToUser();

	public String getQuizCount(String subject) {
		String quizCount = ""; 

		try {
			manager.createConnection();

			String sql = "";
			sql += "SELECT";
			sql += " MAX(CAST(IND AS INTEGER)) AS IND";
			sql += " FROM ABAP";
			sql += " WHERE SUBJECT = ?";

			// SQL 수행
			PreparedStatement pstat = manager.getConnection().prepareStatement(sql);
			pstat.setString(1, subject);
			ResultSet rs = pstat.executeQuery(); 

			while (rs.next()) {
				quizCount = rs.getString("IND");
			}
		} catch (Exception e) {
			logger.info(e.toString());
		} finally {
			manager.closeConnection();
		}

		return quizCount;
	}

	public List<AbapQuizVO> getQuiz(HashMap<String, String> paramMap) {
		List<AbapQuizVO> quizList = new ArrayList<AbapQuizVO>();
		try {
			manager.createConnection();

			String sql = "";
			sql += "SELECT";
			sql += " IND, SUBJECT, MAIN_QUESTION, SUB_QUESTION, ANSWER1, ANSWER2, ANSWER3, ANSWER4, ANSWER5, ANSWER6, COLLECT1, COLLECT2, COLLECT3, COLLECT4, COLLECT5, COLLECT6";
			sql += " FROM ABAP";
			sql += " WHERE IND = ?";
			sql += " AND SUBJECT = ?";

			// SQL 수행
			PreparedStatement pstat = manager.getConnection().prepareStatement(sql);
			pstat.setString(1, paramMap.get("ind"));
			pstat.setString(2, paramMap.get("subject"));
			ResultSet rs = pstat.executeQuery();

			while (rs.next()) {
				AbapQuizVO vo = new AbapQuizVO();

				vo.setInd(rs.getString("IND"));
				vo.setSubject(rs.getString("SUBJECT"));
				vo.setMain_question(rs.getString("MAIN_QUESTION"));
				vo.setSub_question(rs.getString("SUB_QUESTION"));
				vo.setAnswer1(rs.getString("ANSWER1"));
				vo.setAnswer2(rs.getString("ANSWER2"));
				vo.setAnswer3(rs.getString("ANSWER3"));
				vo.setAnswer4(rs.getString("ANSWER4"));
				vo.setAnswer5(rs.getString("ANSWER5"));
				vo.setAnswer6(rs.getString("ANSWER6"));
				vo.setCollect1(rs.getString("COLLECT1"));
				vo.setCollect2(rs.getString("COLLECT2"));
				vo.setCollect3(rs.getString("COLLECT3"));
				vo.setCollect4(rs.getString("COLLECT4"));
				vo.setCollect5(rs.getString("COLLECT5"));
				vo.setCollect6(rs.getString("COLLECT6"));

				quizList.add(vo);
			}

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			manager.closeConnection();
		}

		return quizList;
	}
	
	public List<Sqld30VO> getSqld30(HashMap<String, String> paramMap) {
		List<Sqld30VO> quizList = new ArrayList<Sqld30VO>();
		try {
			manager.createConnection();

			String sql = "";
			sql += "SELECT";
			sql += " IND, QUESTION_GB, SUBJECT, MAIN_QUESTION, SUB_QUESTION, IMG_PATH, ANSWER1, ANSWER2, ANSWER3, ANSWER4, ANSWER5, COLLECT1, COLLECT2, COLLECT3, COLLECT4, COLLECT5";
			sql += " FROM SQLD";
			sql += " WHERE IND = ?";

			// SQL 수행
			PreparedStatement pstat = manager.getConnection().prepareStatement(sql);
			pstat.setString(1, paramMap.get("ind"));
			ResultSet rs = pstat.executeQuery();

			while (rs.next()) {
				Sqld30VO vo = new Sqld30VO();

				vo.setInd(rs.getString("IND"));
				vo.setQuestion_gb(rs.getString("QUESTION_GB"));
				vo.setSubject(rs.getString("SUBJECT"));
				vo.setMain_question(rs.getString("MAIN_QUESTION"));
				vo.setSub_question(rs.getString("SUB_QUESTION"));
				vo.setImg_path(rs.getString("IMG_PATH"));
				vo.setAnswer1(rs.getString("ANSWER1"));
				vo.setAnswer2(rs.getString("ANSWER2"));
				vo.setAnswer3(rs.getString("ANSWER3"));
				vo.setAnswer4(rs.getString("ANSWER4"));
				vo.setAnswer5(rs.getString("ANSWER5"));
				vo.setCollect1(rs.getString("COLLECT1"));
				vo.setCollect2(rs.getString("COLLECT2"));
				vo.setCollect3(rs.getString("COLLECT3"));
				vo.setCollect4(rs.getString("COLLECT4"));
				vo.setCollect5(rs.getString("COLLECT5"));

				quizList.add(vo);
			}

		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			manager.closeConnection();
		}

		return quizList;
	}
	
	public String getSqld30Count() throws Exception {
		String quizCount = "";

		try {
			manager.createConnection();

			String sql = "";
			sql += "SELECT";
			sql += " MAX(CAST(IND AS INTEGER)) AS IND";
			sql += " FROM SQLD";

			// SQL 수행
			PreparedStatement pstat = manager.getConnection().prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();

			while (rs.next()) {
				quizCount = rs.getString("IND");
			}
		} catch (Exception e) {
			logger.error(e.toString());
		} finally {
			manager.closeConnection();
		}

		return quizCount;
	}	
	
	public int insertCollectResult(HashMap<String, String> paramMap) throws Exception {
		int result = 0;

		try {
			user_manager.createConnection();

			String sql = "";
			sql += "INSERT INTO USERLOG VALUES(?, ?, ?)";

			// SQL 수행
			PreparedStatement pstat = user_manager.getConnection().prepareStatement(sql);
			pstat.setString(1, paramMap.get("subject"));
			pstat.setString(2, paramMap.get("ind"));
			pstat.setString(3, paramMap.get("result"));
			
			result = pstat.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			user_manager.closeConnection();
		}

		return result;
	}
	
	public String getCollectCount() throws Exception {
		String result = "";

		try {
			user_manager.createConnection();

			String sql = "";
			sql += "SELECT COUNT(*) AS CNT FROM USERLOG";

			// SQL 수행
			PreparedStatement pstat = user_manager.getConnection().prepareStatement(sql);
			ResultSet rs = pstat.executeQuery();

			while (rs.next()) {
				result = rs.getString("CNT");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			user_manager.closeConnection();
		}
		
		

		return result;
	}
	
	/* 정답률 계산 */
	public String getCollectRate(HashMap<String, String> paramMap) throws Exception {
		String rate = "";

		try {
			user_manager.createConnection();

			String sql = "";
			sql = "SELECT COUNT(*) AS CNT FROM USERLOG WHERE SUBJECT = ? AND IND = ?";

			// SQL 수행
			PreparedStatement pstat = user_manager.getConnection().prepareStatement(sql);
			pstat.setString(1, paramMap.get("subject"));
			pstat.setString(2, paramMap.get("ind"));
			ResultSet rs = pstat.executeQuery();
			
			String totalcnt = "";
			while (rs.next()) {
				totalcnt = rs.getString("CNT");
			}
			
			sql = "SELECT COUNT(*) AS CNT FROM USERLOG WHERE SUBJECT = ? AND IND = ? AND RESULT = 'T'";

			// SQL 수행
			PreparedStatement pstat2 = user_manager.getConnection().prepareStatement(sql);
			pstat2.setString(1, paramMap.get("subject"));
			pstat2.setString(2, paramMap.get("ind"));
			ResultSet rs2 = pstat2.executeQuery();
			
			String collectcnt = "";
			while (rs2.next()) {
				collectcnt = rs2.getString("CNT");
			}
			
			if(Integer.parseInt(totalcnt) > 0) {
				rate = Math.floor(Double.parseDouble(collectcnt)/Double.parseDouble(totalcnt)*1000)/10 + " %";
			} else {
				rate = "???";
			}
					
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			user_manager.closeConnection();
		}
		
		return rate;
	}
	
	/* 오답율 계산 */
	public List<ErrorRateVO> getErrorRateData(HashMap<String, String> paramMap) throws Exception {
		List<ErrorRateVO> resultList = new ArrayList<ErrorRateVO>();

		try {
			user_manager.createConnection();

			String sql = "";
			sql = "SELECT * FROM (SELECT IND, CNT,ROUND((SELECT CAST(COUNT(*) AS REAL) FROM USERLOG WHERE SUBJECT = A.SUBJECT AND IND = A.IND AND RESULT != 'T')*100/CNT, 1) AS ERRORRATE"
					+ " FROM (SELECT IND, COUNT(*) AS CNT, SUBJECT FROM USERLOG WHERE SUBJECT = ? GROUP BY IND) AS A) ORDER BY ERRORRATE DESC LIMIT 20";

			// SQL 수행
			PreparedStatement pstat = user_manager.getConnection().prepareStatement(sql);
			pstat.setString(1, paramMap.get("subject"));
			ResultSet rs = pstat.executeQuery();
			
			while (rs.next()) {
				ErrorRateVO vo = new ErrorRateVO();
				vo.setErrorRate(rs.getString("ERRORRATE"));
				vo.setInd(rs.getString("IND"));
				
				resultList.add(vo);

				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			user_manager.closeConnection();
		}
		
		return resultList;
	}
}
