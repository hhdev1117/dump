package com.daekyo.quiz.com.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.daekyo.quiz.com.service.ComMainService;
import com.daekyo.quiz.com.vo.ErrorRateVO;
import com.daekyo.quiz.util.SQL;

@Service("comMainService")
public class ComMainServiceImpl extends SQL implements ComMainService {

	private static final Logger logger = LoggerFactory.getLogger(ComMainServiceImpl.class);

	@Override
	public int insertCollectResult(HttpServletRequest request) throws Exception {
		int numResult = 0;

		try {
			String subject = "";
			String ind = "";
			String result = "";
			String line = "";

			BufferedReader reader = request.getReader();

			while ((line = reader.readLine()) != null) {
				JSONObject jsonObj = (JSONObject) JSONValue.parse(line);

				subject = (String) jsonObj.get("subject");
				ind = (String) jsonObj.get("ind");
				result = (String) jsonObj.get("result");
			}

			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("subject", subject);
			paramMap.put("ind", ind);
			paramMap.put("result", result);

			numResult = super.insertCollectResult(paramMap);

			// super.getCollectCount();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return numResult;
	}

	@Override
	public List<ErrorRateVO> errorRateForChartData(HttpServletRequest request) {
		List<ErrorRateVO> resultList = new ArrayList<ErrorRateVO>();

		try {
			String subject = "";
			String line = "";

			BufferedReader reader = request.getReader();

			while ((line = reader.readLine()) != null) {
				JSONObject jsonObj = (JSONObject) JSONValue.parse(line);

				subject = (String) jsonObj.get("subject");
			}

			HashMap<String, String> paramMap = new HashMap<String, String>();
			paramMap.put("subject", subject);
			
			resultList = super.getErrorRateData(paramMap);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;

	}

}
