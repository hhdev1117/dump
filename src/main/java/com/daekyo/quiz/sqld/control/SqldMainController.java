package com.daekyo.quiz.sqld.control;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.daekyo.quiz.abap.service.AbapMainService;
import com.daekyo.quiz.sqld.service.SqldMainService;

@Controller
@RequestMapping("/sqld")
public class SqldMainController {

	private static final Logger logger = LoggerFactory.getLogger(SqldMainController.class);

	@Autowired
	MessageSource messageSource;

	@Resource(name = "sqldMainService")
	private SqldMainService sqldMainService;

	/**
	 * 화면
	 */
	@RequestMapping("/sqld30.do")
	public ModelAndView abapQuizMain(HttpServletRequest request, HttpServletResponse response) {
		 ModelAndView mav = new ModelAndView();
		 HttpSession session = request.getSession();
		
		 mav.setViewName("sqld/sqld30");
			
		 return mav;
	}
	
	/**
	 * 문제 조회
	 */
	@ResponseBody
	@RequestMapping("/getSqld30List.do")
	public List getSqld30(HttpServletRequest request, HttpServletResponse response) {

		logger.debug("[ABAP_CONTROLLER] : " + request.getServletPath());

		List resultList = null;

		try {
			resultList = sqldMainService.getSqld30List(request);
		} catch (Exception e) {
			logger.info("[Exception] " + request.getServletPath() + " - " + e );
		}
		return resultList;
	}

}
