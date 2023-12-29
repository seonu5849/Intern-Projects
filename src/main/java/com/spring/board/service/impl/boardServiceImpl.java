package com.spring.board.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.BoardDao;
import com.spring.board.service.boardService;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.ComCodeVo;
import com.spring.board.vo.PageVo;

@Service
public class boardServiceImpl implements boardService{
	
	@Autowired
	BoardDao boardDao;
	
	@Override
	public String selectTest() throws Exception {
		// TODO Auto-generated method stub
		return boardDao.selectTest();
	}
	
	@Override
	public List<BoardVo> SelectBoardList(PageVo pageVo, String[] codeIds) throws Exception {
		// TODO Auto-generated method stub
		
		List<String> list = new ArrayList<>();
		
		if(codeIds != null) {
			for(String codeId : codeIds) {
				list.add(codeId);
			}
		}
		
		return boardDao.selectBoardList(pageVo, list);
	}
	
	@Override
	public int selectBoardCnt(String[] codeIds) throws Exception {
		// TODO Auto-generated method stub
		
		List<String> list = new ArrayList<>();
		
		if(codeIds != null) {
			for(String codeId : codeIds) {
				list.add(codeId);
			}
		}
		
		return boardDao.selectBoardCnt(list);
	}
	
	@Override
	public BoardVo selectBoard(String boardType, int boardNum) throws Exception {
		// TODO Auto-generated method stub
		BoardVo boardVo = new BoardVo();
		
		boardVo.setBoardType(boardType);
		boardVo.setBoardNum(boardNum);
		
		return boardDao.selectBoard(boardVo);
	}
	
	@Override
	public int boardInsert(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.boardInsert(boardVo);
	}

	@Override
	public Integer boardUpdate(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		
		return boardDao.boardUpdate(boardVo);
	}

	@Override
	public Integer boardDelete(BoardVo boardVo) throws Exception {
		// TODO Auto-generated method stub
		return boardDao.boardDelete(boardVo);
	}

	@Override
	public List<ComCodeVo> selectComCodeList() throws Exception {
		// TODO Auto-generated method stub
		return boardDao.selectAllComCode();
	}

	
	
}
