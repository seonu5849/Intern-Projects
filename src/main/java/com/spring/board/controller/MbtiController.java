package com.spring.board.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.spring.board.HomeController;
import com.spring.board.service.MbtiService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.MbtiVo;
import com.spring.board.vo.PageVo;
import com.spring.common.CommonUtil;

@Controller
public class MbtiController {
	
	@Autowired 
	MbtiService mbtiService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value="/mbti/view.do", method=RequestMethod.GET)
	public String mbtiView(Model model, PageVo pageVo) throws Exception {
		
		int page = 1;
		
		System.out.println("pageNO::"+pageVo.getPageNo());
		if(pageVo.getPageNo() == 0 || pageVo.getPageNo() == null){
			pageVo.setPageNo(page);
		}
		pageVo.setPerPage(5);
		
		List<BoardVo> mbtiList = this.mbtiService.findAllMbti(pageVo);
		Integer totalCnt = this.mbtiService.mbtiTotalCnt();
		pageVo.setTotalPage(totalCnt);
		
		int pageNo = pageVo.getPageNo()/5 + 1;
		pageVo.setPageNo(pageNo);
		
		model.addAttribute("list", mbtiList);
		model.addAttribute("page", pageVo);
		
		return "/mbti/mbtiList";
	}
	
	@ResponseBody
	@RequestMapping(value="/mbti/pagingAction.do", method=RequestMethod.GET)
	public Map<String, Object> mbtiPagingAction(Model model, PageVo pageVo) throws Exception {
		
		Map<String, Object> map = new HashMap<>();
		
		int page = 1;
		
		System.out.println("pageNO::"+pageVo.getPageNo());
		if(pageVo.getPageNo() == 0 || pageVo.getPageNo() == null){
			pageVo.setPageNo(page);
		}
		pageVo.setPerPage(5);
		
		Integer totalCnt = this.mbtiService.mbtiTotalCnt();
		pageVo.setTotalPage(totalCnt);
		List<BoardVo> mbtiList = this.mbtiService.findAllMbti(pageVo);
		
		map.put("page", pageVo);
		map.put("list", mbtiList);
		
		return map;
	}
	
	@RequestMapping(value="/mbti/action.do", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> mbtiAction(Model model, HttpServletRequest request) throws Exception {
		
		Map<String, String> map = new HashMap<>();
		String[] optionsArray = request.getParameterValues("options");
		String radioLength = request.getParameter("length");

		if(optionsArray != null) {
			String mbti = this.mbtiService.calculateMbti(optionsArray, Integer.parseInt(radioLength));
			map.put("mbti", mbti);
		}
		
		return map;
	}
	
//	@RequestMapping(value="/mbti/result.do", method=RequestMethod.GET)
//	public String mbtiResult(Model model, String mbti, RedirectAttributes redirectAttr) {
//		
////		model.addAttribute("mbti", mbti);
////		redirectAttr.addFlashAttribute("mbti",mbti);
//		
//		return "redirect:mbtiResult";
//	}
	
}
