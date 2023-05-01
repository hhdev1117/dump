package com.daekyo.quiz.sqld.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import com.daekyo.quiz.sqld.vo.Sqld30VO;

public interface SqldMainService {

	public List<Sqld30VO> getSqld30List(HttpServletRequest request) throws Exception;

}
