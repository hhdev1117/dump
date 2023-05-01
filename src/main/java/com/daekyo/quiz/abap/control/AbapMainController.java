package com.daekyo.quiz.abap.control;

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

@Controller
@RequestMapping("/abap")
public class AbapMainController {

	private static final Logger logger = LoggerFactory.getLogger(AbapMainController.class);

	@Autowired
	MessageSource messageSource;

	@Resource(name = "abapMainService")
	private AbapMainService abapMainService;

	/**
	 * 화면
	 */
	@RequestMapping("/quiz1.do")
	public ModelAndView abapQuizMain(HttpServletRequest request, HttpServletResponse response) {
		 ModelAndView mav = new ModelAndView();
		 HttpSession session = request.getSession();
		
		 mav.setViewName("abap/quiz1");
			
		 return mav;
	}
	
	@RequestMapping("/quiz2.do")
	public ModelAndView abapQuizMain2(HttpServletRequest request, HttpServletResponse response) {
		 ModelAndView mav = new ModelAndView();
		 HttpSession session = request.getSession();
		
		 mav.setViewName("abap/quiz2");
			
		 return mav;
	}
	
	/**
	 * 문제 조회
	 */
	@ResponseBody
	@RequestMapping("/getQuiz.do")
	public List getQuiz(HttpServletRequest request, HttpServletResponse response) {

		logger.debug("[ABAP_CONTROLLER] : " + request.getServletPath());

		List resultList = null;

		try {
			resultList = abapMainService.getQuizList(request);
		} catch (Exception e) {
			logger.info("[Exception] " + request.getServletPath() + " - " + e );
		}
		return resultList;
	}

}
