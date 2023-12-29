package com.spring.board.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.board.dao.UserDao;
import com.spring.board.service.UserService;
import com.spring.board.vo.UserVo;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao userDao;
	
	@Override
	public Integer userJoin(UserVo userVo) throws Exception {
		
		Integer duplicate = checkDuplicateUserId(userVo);
		
		if(duplicate == 1) {
			return 0;
		}
		
		return this.userDao.insertUser(userVo);
	}

	@Override
	public Integer checkDuplicateUserId(UserVo userVo) throws Exception {
		
		if(userVo.getUserId().isEmpty()) {
			return 1;
		}
		
		return this.userDao.checkDuplicationUserId(userVo);
	}

	@Override
	public String userLogin(UserVo userVo) throws Exception {
		
		UserVo findUser = this.userDao.selectUserByIdAndPw(userVo);
		Integer duplicate = checkDuplicateUserId(userVo);
		
		if(duplicate == 1 && findUser != null) { // 로그인시 아이디가 일치할 경우
			
			if(userVo.getUserPw().equals(findUser.getUserPw())) {
				return "LoginSuccess";
			}else{
				return "IdOkButPwNo";
			}
			
		}else if(duplicate == 1 && findUser == null){
			return "IdOkButPwNo";
		}else { // 입력한 아이디가 일치하는 계정이 없을 경우
			return "LoginFail";
		}
		
	}

	@Override
	public UserVo findUser(UserVo userVo) throws Exception {
		// TODO Auto-generated method stub
		return this.userDao.selectUserByIdAndPw(userVo);
	}
	
	

}
