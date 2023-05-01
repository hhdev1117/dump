package com.daekyo.quiz.com.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.daekyo.quiz.com.vo.ErrorRateVO;

public interface ComMainService {

	public int insertCollectResult(HttpServletRequest request) throws Exception;
	
	public List<ErrorRateVO> errorRateForChartData(HttpServletRequest request) throws Exception;

}
