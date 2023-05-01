package com.daekyo.quiz.sqld.service.impl;

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

import com.daekyo.quiz.sqld.service.SqldMainService;
import com.daekyo.quiz.sqld.vo.Sqld30VO;
import com.daekyo.quiz.util.SQL;

@Service("sqldMainService")
public class SqldMainServiceImpl extends SQL implements SqldMainService {

	private static final Logger logger = LoggerFactory.getLogger(SqldMainServiceImpl.class);

	@Override
	public List<Sqld30VO> getSqld30List(HttpServletRequest request) throws Exception {
		List<Sqld30VO> resultList = new ArrayList<Sqld30VO>();
		try {
			String ind = "";
			String line = "";
			
			BufferedReader reader = request.getReader();
			
			while ((line = reader.readLine()) != null) {
				JSONObject jsonObj = (JSONObject) JSONValue.parse(line);
	
				ind = (String) jsonObj.get("ind");
			}
			
			
			String quizCount = super.getSqld30Count();
			
			HashMap<String, String> paramMap = new HashMap<String, String>();
			if(ind.equals("0")) 
				paramMap.put("ind", ((int) (Math.random() * Integer.parseInt(quizCount) + 1)) + "");
			else 
				paramMap.put("ind", Integer.parseInt(ind) > Integer.parseInt(quizCount) ? quizCount : ind);
			
			resultList = super.getSqld30(paramMap); 
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

}
