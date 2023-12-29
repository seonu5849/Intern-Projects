package com.spring.board.service;

import java.util.List;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.PageVo;

public interface MbtiService {


	public abstract List<BoardVo> findAllMbti(PageVo pageVo) throws Exception;
	
	public abstract String calculateMbti(String[] optionsArray, Integer radioLength) throws Exception;
	
	public abstract Integer mbtiTotalCnt() throws Exception;
	
}
