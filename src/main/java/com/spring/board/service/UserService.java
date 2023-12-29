package com.spring.board.service;

import com.spring.board.vo.UserVo;

public interface UserService {

	public abstract Integer userJoin(UserVo userVo) throws Exception; // 회원가입
	
	public abstract Integer checkDuplicateUserId(UserVo userVo) throws Exception; // 중복검사
	
	public abstract String userLogin(UserVo userVo) throws Exception; // 로그인
	
	public abstract UserVo findUser(UserVo userVo) throws Exception;
	
}
