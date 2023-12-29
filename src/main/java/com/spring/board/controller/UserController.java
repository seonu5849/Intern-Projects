package com.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.board.HomeController;
import com.spring.board.service.UserService;
import com.spring.board.service.boardService;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.UserVo;
import com.spring.common.CommonUtil;

@Controller
public class UserController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private final boardService boardService;
	private final UserService userService;
	
	@Autowired
	public UserController(boardService boardService, UserService userService) {
		this.boardService = boardService;
		this.userService = userService;
	}
	
	
	@RequestMapping(value="/user/login.do", method = RequestMethod.GET)
	public String userLoginView(Locale locale) {
		
		return "user/login";
	}
	
	@RequestMapping(value="/user/join.do", method = RequestMethod.GET)
	public String userJoinView(Locale locale, Model model) throws Exception {
		
		List<ComCodeVo> comCodeVo = this.boardService.selectComCodeList();
		
		model.addAttribute("codes", comCodeVo);
		
		return "user/join";
	}
	
	@RequestMapping(value="/user/joinAction.do", method=RequestMethod.POST)
	@ResponseBody
	public String userJoinAction(Locale locale, UserVo userVo) throws Exception {
		
		Map<String, String> result = new HashMap<>();
		CommonUtil commonUtil = new CommonUtil();
		
		Integer affectedRows = this.userService.userJoin(userVo);
		
		result.put("success", (affectedRows > 0)? "Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ", result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		
		return callbackMsg;
	}
	
	@RequestMapping(value="/user/duplicateAction.do", method=RequestMethod.POST)
	@ResponseBody
	public String userDuplicateId(Locale locale, UserVo userVo) throws Exception {
		Map<String, String> result = new HashMap<>();
		CommonUtil commonUtil = new CommonUtil();
		
		Integer duplicateId = this.userService.checkDuplicateUserId(userVo);
		
		result.put("success", (duplicateId > 0)? "Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ", result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		
		return callbackMsg;
	}
	
	@RequestMapping(value="/user/loginAction.do", method=RequestMethod.POST, produces = "application/text; charset=UTF-8")
	@ResponseBody
	public String loginAction(Locale locale, UserVo userVo, HttpServletRequest req) throws Exception {
		Map<String, Object> result = new HashMap<>();
		CommonUtil commonUtil = new CommonUtil();
		HttpSession session = req.getSession();
		
		String availability = this.userService.userLogin(userVo);
		String successResult;
		UserVo findUserVO = null;
		
		switch(availability) {
			case "LoginSuccess" : {
				findUserVO = this.userService.findUser(userVo);
				session.setAttribute("user", findUserVO);
				successResult = "LoginSuccess";
				break;
			}
			case "IdOkButPwNo" : {
				successResult = "IdOkButPwNo";
				break;
			}
			default : {
				successResult = "LoginFail";
				break;
			}
		}
		System.out.println("successResult::"+successResult);
		result.put("success", successResult);
		result.put("findUserName", (findUserVO != null && findUserVO.getUserName() != null) ? findUserVO.getUserName() : "");
		
		String callbackMsg = commonUtil.getJsonCallBackString(" ", result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		
		return callbackMsg;
	}
	
	@RequestMapping(value="/user/logoutAction.do", method=RequestMethod.GET)
	public String logoutAction(HttpServletRequest httpServletRequest) {
		
		HttpSession session = httpServletRequest.getSession();
		session.invalidate();
		
		return "redirect: /board/boardList.do";
	}
	
	
}
