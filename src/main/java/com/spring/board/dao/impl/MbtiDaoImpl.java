package com.spring.board.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.dao.MbtiDao;
import com.spring.board.vo.BoardVo;
import com.spring.board.vo.PageVo;

@Repository
public class MbtiDaoImpl implements MbtiDao{
	
	@Autowired
	private SqlSession sqlSession;
	private Map<String, Integer> result = new HashMap<>();
	
	@Override
	public List<BoardVo> selectAllMbti(PageVo pageVo) throws Exception {
		return this.sqlSession.selectList("mbti.mbtiList", pageVo);
	}
	
	@Override
	public Map<String, Integer> getResult() throws Exception {
		return result;
	}

	@Override
	public void initMap() throws Exception {
		this.result.put("E", 0);
		this.result.put("I", 0);
		this.result.put("S", 0);
		this.result.put("N", 0);
		this.result.put("F", 0);
		this.result.put("T", 0);
		this.result.put("P", 0);
		this.result.put("J", 0);
	}

	@Override
	public Integer selectMbtiTotalCnt() throws Exception {
		return this.sqlSession.selectOne("mbti.mbtiTotalCnt");
	}
	
	
}
