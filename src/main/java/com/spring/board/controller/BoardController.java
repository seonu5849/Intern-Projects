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

import com.spring.board.HomeController;
import com.spring.board.service.boardService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;
import com.spring.common.CommonUtil;

@Controller
public class BoardController {
	
	@Autowired 
	boardService boardService;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@RequestMapping(value = "/board/boardList.do", method = RequestMethod.GET)
	public String boardList(Locale locale, Model model, PageVo pageVo, String[] codeId) throws Exception{
		
		System.out.println("pageVo::"+pageVo.getPageNo());
		
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		System.out.println("page1::"+pageVo.getPageNo());
		int page = 1;
		int totalCnt = 0;
		
		if(pageVo.getPageNo() == 0 || pageVo.getPageNo() == null){
			pageVo.setPageNo(page);
		}
		
		System.out.println("page2::"+pageVo.getPageNo());
		
		boardList = boardService.SelectBoardList(pageVo, codeId);
		totalCnt = boardService.selectBoardCnt(codeId);
		List<ComCodeVo> comCodeList = boardService.selectComCodeList();
		pageVo.setTotalPage(totalCnt);
		pageVo.setTotalBlocks(pageVo.getTotalPage());
		pageVo.setCurrentBlock(pageVo.getPageNo());
		
		System.out.println("totalPageCnt::"+pageVo.getTotalPage());
		System.out.println("totalBlocks::"+pageVo.getTotalBlocks());
		System.out.println("currentBlock::"+pageVo.getCurrentBlock());
		System.out.println("beginPageNo::"+pageVo.getBeginPageNo());
		System.out.println("endPageNo::"+pageVo.getEndPageNo());
		
		model.addAttribute("boardList", boardList);
		model.addAttribute("totalCnt", totalCnt);
		model.addAttribute("page", pageVo);
		model.addAttribute("ComCodes", comCodeList);
		
		return "board/boardList";
	}
	
	@RequestMapping(value = "/board/{boardType}/{boardNum}/boardView.do", method = RequestMethod.GET)
	public String boardView(Locale locale, Model model
			,@PathVariable("boardType")String boardType
			,@PathVariable("boardNum")int boardNum) throws Exception{
		
		BoardVo boardVo = new BoardVo();
		
		
		boardVo = boardService.selectBoard(boardType,boardNum);
		
		model.addAttribute("boardType", boardType);
		model.addAttribute("boardNum", boardNum);
		model.addAttribute("board", boardVo);
		
		return "board/boardView";
	}
	
	@RequestMapping(value = "/board/boardWrite.do", method = RequestMethod.GET)
	public String boardWrite(Locale locale, Model model) throws Exception{
		
		List<ComCodeVo> list = boardService.selectComCodeList();
		
		model.addAttribute("ComCodes", list);
		
		
		return "board/boardWrite";
	}
	
	@RequestMapping(value = "/board/boardWriteAction.do", method = RequestMethod.POST)
	@ResponseBody
	public String boardWriteAction(Locale locale,BoardVo boardVo) throws Exception{
		
		
		HashMap<String, String> result = new HashMap<String, String>();
		CommonUtil commonUtil = new CommonUtil();
		
		int resultCnt = boardService.boardInsert(boardVo);
		
		result.put("success", (resultCnt > 0)?"Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ",result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		
		return callbackMsg;
	}
	
	@RequestMapping(value = "/board/{boardType}/{boardNum}/boardUpdateView.do", method = RequestMethod.GET)
	public String boardUpdate(Locale locale, Model model,
			@PathVariable("boardType")String boardType
			,@PathVariable("boardNum")Integer boardNum) throws Exception {
		System.out.println("type : "+boardType);
		System.out.println("num : "+boardNum);
		
		BoardVo boardVo = boardService.selectBoard(boardType, boardNum);
		
		model.addAttribute("board", boardVo);
		
		return "/board/boardUpdateView";
	}
	
	@RequestMapping(value = "/board/boardUpdateAction.do", method = RequestMethod.PUT)
	@ResponseBody
	public String boardUpdateAction(Locale locale, BoardVo boardVo) throws Exception {
		
		Map<String, String> result = new HashMap<>();
		CommonUtil commonUtil = new CommonUtil();
		
		Integer affectedRows = boardService.boardUpdate(boardVo);
		
		result.put("success", (affectedRows > 0)?"Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ", result);

		System.out.println("callbackMsg:: "+callbackMsg);
		
		return callbackMsg;
	}
	
	@RequestMapping(value = "/board/boardDeleteAction.do", method = RequestMethod.DELETE)
	@ResponseBody
	public String boardDeleteAction(Locale locale, BoardVo boardVo) throws Exception {
		System.out.println("type : "+boardVo.getBoardType());
		System.out.println("num : "+boardVo.getBoardNum());
		
		Map<String, String> result = new HashMap<>();
		CommonUtil commonUtil = new CommonUtil();
		
		Integer affectedRows = boardService.boardDelete(boardVo);
		
		result.put("success", (affectedRows > 0)? "Y":"N");
		String callbackMsg = commonUtil.getJsonCallBackString(" ", result);
		
		System.out.println("callbackMsg::"+callbackMsg);
		
		return callbackMsg;
	}
	
	@RequestMapping(value ="/board/boardSearch.do", method=RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> boardSearchAction(Locale locale, PageVo pageVo, String[] codeId) throws Exception {
		
		System.out.println("pageNo::"+pageVo.getPageNo());
		System.out.println("checked::"+Arrays.toString(codeId));
		
		Map<String, Object> map = new HashMap<>();
		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		int page = 1;
		int totalCnt = 0;
		
		if(pageVo.getPageNo() == 0 || pageVo.getPageNo() == null){
			pageVo.setPageNo(page);
		}
		
		boardList = boardService.SelectBoardList(pageVo, codeId); // 검색결과 또는 페이지
		totalCnt = boardService.selectBoardCnt(codeId); // 총 검색 걸과

		pageVo.setTotalPage(totalCnt); // 총 페이지 개수
		pageVo.setTotalBlocks(pageVo.getTotalPage()); // 총 블럭 수
		pageVo.setCurrentBlock(pageVo.getPageNo()); // 현재 블럭 번호
		
		map.put("boardList", boardList);
		map.put("totalCnt", totalCnt);
		map.put("page", pageVo);
		
		return map;
	}
	
}
