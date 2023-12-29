package com.spring.board.dao;

import java.util.List;

import com.spring.board.vo.CareerVo;
import com.spring.board.vo.CertificateVo;
import com.spring.board.vo.EducationVo;
import com.spring.board.vo.RecruitVo;
import com.spring.board.vo.UserSaveInfo;

public interface RecruitDao {

	public abstract RecruitVo selectUser(RecruitVo recruitVo) throws Exception;
	
	public abstract List<EducationVo> selectEducationDetail(String seq) throws Exception;
	
	public abstract List<CareerVo> selectCareerDetail(String seq) throws Exception;
	
	public abstract List<CertificateVo> selectCertificateDetail(String seq) throws Exception;
	
	public abstract Integer selectUserByNameAndPhone(RecruitVo recruitVo) throws Exception;
	
	public abstract Integer mergeRecruit(RecruitVo recruitVo) throws Exception;
	
	public abstract Integer mergeEducation(EducationVo educationVo) throws Exception;
	
	public abstract Integer mergeCareer(CareerVo careerVo) throws Exception;
	
	public abstract Integer mergeCertificate(CertificateVo certificateVo) throws Exception;
	
	public abstract Integer deleteEducation(String eduSeq) throws Exception;
	
	public abstract Integer deleteCareer(String carSeq) throws Exception;
	
	public abstract Integer deleteCertificate(String certSeq) throws Exception;

	public abstract UserSaveInfo selectUserInfoEducationDetail(String seq) throws Exception;
	
	public abstract UserSaveInfo selectUserInfoCareerDetail(String seq) throws Exception;
	
	
}
