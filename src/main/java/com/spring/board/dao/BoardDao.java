package com.spring.board.dao;

import java.util.List;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;

public interface BoardDao {

	public String selectTest() throws Exception;

	public List<BoardVo> selectBoardList(PageVo pageVo, List<String> codeId) throws Exception;

	public BoardVo selectBoard(BoardVo boardVo) throws Exception;

	public int selectBoardCnt(List<String> codeIds) throws Exception;

	public int boardInsert(BoardVo boardVo) throws Exception;
	
	public Integer boardUpdate(BoardVo boardVo) throws Exception;
	
	public Integer boardDelete(BoardVo boardVo) throws Exception;
	
	public List<ComCodeVo> selectAllComCode() throws Exception;

}
