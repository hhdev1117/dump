package com.daekyo.quiz.abap.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.daekyo.quiz.abap.vo.AbapQuizVO;

public interface AbapMainService {

	public List<AbapQuizVO> getQuizList(HttpServletRequest request) throws Exception;

}
