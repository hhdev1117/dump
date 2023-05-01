package com.daekyo.quiz.abap.service.impl;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.daekyo.quiz.abap.service.AbapMainService;
import com.daekyo.quiz.abap.vo.AbapQuizVO;
import com.daekyo.quiz.util.SQL;

@Service("abapMainService")
public class AbapMainServiceImpl extends SQL implements AbapMainService {

	private static final Logger logger = LoggerFactory.getLogger(AbapMainServiceImpl.class);

	@Override
	public List<AbapQuizVO> getQuizList(HttpServletRequest request) throws Exception {
		List<AbapQuizVO> resultList = new ArrayList<AbapQuizVO>();
		try {
			String ind = "";
			String subject = "";
			String line = "";
			
			BufferedReader reader = request.getReader();
			
			while ((line = reader.readLine()) != null) {
				JSONObject jsonObj = (JSONObject) JSONValue.parse(line);
	
				ind = (String) jsonObj.get("ind");
				subject = (String) jsonObj.get("subject");
			}
			
			String quizCount = super.getQuizCount(subject);
			
			
			HashMap<String, String> paramMap = new HashMap<String, String>();
			if(ind.equals("0")) {
				paramMap.put("ind", ((int) (Math.random() * Integer.parseInt(quizCount) + 1)) + "");
				paramMap.put("subject", subject);
			} else { 
				paramMap.put("ind", Integer.parseInt(ind) > Integer.parseInt(quizCount) ? quizCount : ind);
				paramMap.put("subject", subject);
			}
			resultList = super.getQuiz(paramMap); 
			resultList.get(0).setRate(super.getCollectRate(paramMap));
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}
	

}
