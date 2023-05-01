package com.daekyo.quiz.com.control;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.daekyo.quiz.com.service.ComMainService;

@Controller
@RequestMapping("/com")
public class ComMainController {

	private static final Logger logger = LoggerFactory.getLogger(ComMainController.class);

	@Autowired
	MessageSource messageSource;

	@Resource(name = "comMainService")
	private ComMainService comMainService;

	/**
	 * 결과저장
	 */
	@ResponseBody
	@RequestMapping("/insertCollectResult.do")
	public int insertCollectResult(HttpServletRequest request, HttpServletResponse response) {

		logger.debug("[COM_CONTROLLER] : " + request.getServletPath());

		int result = 0;

		try {
			result = comMainService.insertCollectResult(request);
		} catch (Exception e) {
			logger.info("[Exception] " + request.getServletPath() + " - " + e );
		}
		return result;
	}
	
	/**
	 * 결과저장
	 */
	@ResponseBody
	@RequestMapping("/getErrorRateForChartData.do")
	public List errorRateForChartData(HttpServletRequest request, HttpServletResponse response) {

		logger.debug("[COM_CONTROLLER] : " + request.getServletPath());

		List resultList = new ArrayList();

		try {
			resultList = comMainService.errorRateForChartData(request);
		} catch (Exception e) {
			logger.info("[Exception] " + request.getServletPath() + " - " + e );
		}
		return resultList;
	}

}
