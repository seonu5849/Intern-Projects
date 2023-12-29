package com.spring.board.dao.impl;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.dao.UserDao;
import com.spring.board.vo.UserVo;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public Integer insertUser(UserVo userVo) throws Exception {
		return this.sqlSession.insert("user.insertUser", userVo);
	}

	@Override
	public Integer checkDuplicationUserId(UserVo userVo) throws Exception {
		return this.sqlSession.selectOne("user.checkDuplicationUserId", userVo);
	}

	@Override
	public UserVo selectUserByIdAndPw(UserVo userVo) throws Exception {
		// TODO Auto-generated method stub
		return this.sqlSession.selectOne("user.selectUserByIdAndPw", userVo);
	}

}
