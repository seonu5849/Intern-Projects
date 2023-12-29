package com.spring.board.service;

import java.util.List;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;

public interface boardService {

	public String selectTest() throws Exception;

	public List<BoardVo> SelectBoardList(PageVo pageVo, String[] codeIds) throws Exception;

	public BoardVo selectBoard(String boardType, int boardNum) throws Exception;

	public int selectBoardCnt(String[] codeIds) throws Exception;

	public int boardInsert(BoardVo boardVo) throws Exception;
	
	public Integer boardUpdate(BoardVo boardVo) throws Exception;

	public Integer boardDelete(BoardVo boardVo) throws Exception;
	
	public List<ComCodeVo> selectComCodeList() throws Exception;
}
