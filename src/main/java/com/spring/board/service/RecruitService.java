package com.spring.board.service;

import java.util.List;

import com.spring.board.vo.CareerVo;
import com.spring.board.vo.CertificateVo;
import com.spring.board.vo.EducationVo;
import com.spring.board.vo.NationwideAddrVo;
import com.spring.board.vo.RecruitTotalInputData;
import com.spring.board.vo.RecruitVo;
import com.spring.board.vo.UserSaveInfo;

public interface RecruitService {

	public abstract RecruitVo findUserByNameAndPhone(RecruitVo recruitVo) throws Exception;
	
	public abstract List<EducationVo> findUserEducation(String seq) throws Exception;
	
	public abstract List<CareerVo> findUserCareer(String seq) throws Exception;
	
	public abstract List<CertificateVo> findUserCertificate(String seq) throws Exception;
	
	public abstract NationwideAddrVo jsonParseNationwideAddr() throws Exception;
	
	public abstract Integer validateUser(RecruitVo recruitVo) throws Exception;
	
	public abstract Integer saveRecruit(RecruitTotalInputData data) throws Exception;

	public abstract Integer submitRecruit(RecruitTotalInputData data) throws Exception;
	
	public abstract Integer deleteEducations(String[] eduSeqs) throws Exception;
	
	public abstract Integer deleteCareers(String[] carSeqs) throws Exception;
	
	public abstract Integer deleteCertifiacates(String[] certSeqs) throws Exception;
	
	public abstract UserSaveInfo findUserEducationDetail(String seq) throws Exception;
	
	public abstract UserSaveInfo findUserCareerDetail(String seq) throws Exception;
}
