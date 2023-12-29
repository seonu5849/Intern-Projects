package com.spring.board.dao;

import java.util.List;
import java.util.Map;

import com.spring.board.vo.BoardVo;
import com.spring.board.vo.PageVo;

public interface MbtiDao {

	public abstract List<BoardVo> selectAllMbti(PageVo pageVo) throws Exception;
	public abstract Map<String, Integer> getResult() throws Exception;
	public abstract void initMap() throws Exception;
	public abstract Integer selectMbtiTotalCnt() throws Exception;
}
