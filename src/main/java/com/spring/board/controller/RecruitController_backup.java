package com.spring.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.spring.board.HomeController;
import com.spring.board.service.RecruitService;
import com.spring.board.vo.CareerVo;
import com.spring.board.vo.CertificateVo;
import com.spring.board.vo.EducationVo;
import com.spring.board.vo.NationwideAddrVo;
import com.spring.board.vo.RecruitTotalInputData;
import com.spring.board.vo.RecruitVo;
import com.spring.board.vo.UserSaveInfo;

@Controller
public class RecruitController_backup {

//	private final RecruitService recruitService;
//	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
//	
//	@Autowired
//	public RecruitController_backup(RecruitService recruitService) {
//		this.recruitService = recruitService;
//	}
//	
//	@RequestMapping(value="/recruit/login.do", method=RequestMethod.GET)
//	public String loginView() {
//		
//		return "/recruit/login";
//	}
////	produces = "application/text; charset=UTF-8"
//	@ResponseBody
//	@RequestMapping(value="/recruit/login.do", method=RequestMethod.POST)
//	public Map<String, String> loginAction(HttpServletRequest res, @RequestBody RecruitVo recruitVo) throws Exception {
//		
//		HttpSession session = (HttpSession) res.getSession();
//		
//		session.setAttribute("user", recruitVo);
//		
////		return "redirect:/recruit/main.do";
//		
//		Map<String, String> response = new HashMap<>();
//	    response.put("redirect", "/recruit/main.do");
//	    return response;
//	}
//	
//	@RequestMapping(value="/recruit/main.do", method=RequestMethod.GET)
//	public String mainView(Model model, HttpSession session) throws Exception {
//		RecruitVo recruitVo;
//		Integer validateUser;
//		UserSaveInfo userSubmitInfo = null;
//		
//		System.out.println("mainView");
//		
//		recruitVo = (RecruitVo) session.getAttribute("user");
//		NationwideAddrVo nationwideAddrVo = this.recruitService.jsonParseNationwideAddr();
//		
//		System.out.println("recruitVo: "+recruitVo);
//		
//		if(recruitVo != null) {
//			validateUser = this.recruitService.validateUser(recruitVo);
//		}else {
//			return "redirect:/recruit/login.do";
//		}
//		
//		if(validateUser != 0) { // 이름과 휴대폰번호로 조회했을때 나오면 저장을 한 회원이다.
//			// 여기서 아이디와 휴대폰번호로 조회해서 나오는 정보들을 recuritVo에 다시 담아서 보내준다.
//			RecruitVo findUser = this.recruitService.findUserByNameAndPhone(recruitVo);
//			List<EducationVo> findUserEducation = this.recruitService.findUserEducation(findUser.getSeq());
//			List<CareerVo> findUserCareer = this.recruitService.findUserCareer(findUser.getSeq());
//			List<CertificateVo> findUserCertificate = this.recruitService.findUserCertificate(findUser.getSeq());
//			
//			userSubmitInfo = this.recruitService.findUserEducationDetail(findUser.getSeq());
//			model.addAttribute("submitEducationInfo", userSubmitInfo);
//			userSubmitInfo = this.recruitService.findUserCareerDetail(findUser.getSeq());
//			model.addAttribute("submitCareerInfo", userSubmitInfo);			
//			
//			model.addAttribute("recruit", findUser);
//			model.addAttribute("educations", findUserEducation);
//			model.addAttribute("careers", findUserCareer);
//			model.addAttribute("certificates", findUserCertificate);
//		}else {
//			model.addAttribute("recruit", recruitVo);
//		}
//		
//		model.addAttribute("address", nationwideAddrVo.getRegcodes());
//		
//		return "/recruit/main";
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/recruit/main/save.do", method=RequestMethod.POST)
//	public Map<String, Object> saveAction(@RequestBody RecruitTotalInputData data) throws Exception {
//		
//		Map<String, Object> map = new HashMap<>();
//
//		Integer saveRecruitResult = this.recruitService.saveRecruit(data);
//		
//		System.out.println("saveRecruitResult: "+saveRecruitResult);
//		
//		map.put("result", saveRecruitResult);
//
//		return map;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/recruit/main/submit.do", method=RequestMethod.POST)
//	public Map<String, Object> submitAction(@RequestBody RecruitTotalInputData data) throws Exception {
//		
//		Map<String, Object> map = new HashMap<>();
//
//		Integer submitRecruitResult = this.recruitService.submitRecruit(data);
//		
//		System.out.println("saveRecruitResult: "+submitRecruitResult);
//		
//		map.put("result", submitRecruitResult);
//
//		return map;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/recruit/addressAPI.do", method=RequestMethod.GET)
//	public Map<String, Object> addressAPI() throws Exception{
//		Map<String, Object> map = new HashMap<>();
//		
//		NationwideAddrVo nationwideAddrVo = this.recruitService.jsonParseNationwideAddr();
//		List<EducationVo> findUserEducation = this.recruitService.findUserEducation("1");
//		map.put("addr", nationwideAddrVo.getRegcodes());
//		map.put("user", findUserEducation);
//		
//		return map;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/recruit/education.do", method=RequestMethod.DELETE)
//	public Map<String, Integer> eduDeleteAction(@RequestParam("seqs") String[] eduSeqs) throws Exception{
//		Map<String, Integer> map = new HashMap<>();
//		Integer affectedRows = this.recruitService.deleteEducations(eduSeqs);
//		
//		map.put("result", affectedRows);
//		
//		return map;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/recruit/career.do", method=RequestMethod.DELETE)
//	public Map<String, Integer> careerDeleteAction(@RequestParam("seqs") String[] carSeqs) throws Exception{
//		Map<String, Integer> map = new HashMap<>();
//		Integer affectedRows = this.recruitService.deleteCareers(carSeqs);
//		
//		map.put("result", affectedRows);
//		
//		return map;
//	}
//	
//	@ResponseBody
//	@RequestMapping(value="/recruit/certificaate.do", method=RequestMethod.DELETE)
//	public Map<String, Integer> certDeleteAction(@RequestParam("seqs") String[] certSeqs) throws Exception{
//		Map<String, Integer> map = new HashMap<>();
//		Integer affectedRows = this.recruitService.deleteCertifiacates(certSeqs);
//		
//		map.put("result", affectedRows);
//		
//		return map;
//	}
}
