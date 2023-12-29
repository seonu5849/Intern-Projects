package com.spring.board.dao;

import com.spring.board.vo.UserVo;

public interface UserDao {

	public abstract Integer insertUser(UserVo userVo) throws Exception; // 회원가입
	
	public abstract Integer checkDuplicationUserId(UserVo userVo) throws Exception; // 중복검사
	
	public abstract UserVo selectUserByIdAndPw(UserVo userVo) throws Exception; // 로그인
	
}
