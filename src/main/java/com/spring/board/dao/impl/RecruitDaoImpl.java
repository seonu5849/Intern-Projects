package com.spring.board.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.spring.board.dao.RecruitDao;
import com.spring.board.vo.CareerVo;
import com.spring.board.vo.CertificateVo;
import com.spring.board.vo.EducationVo;
import com.spring.board.vo.RecruitTotalInputData;
import com.spring.board.vo.RecruitVo;
import com.spring.board.vo.UserSaveInfo;

@Repository
public class RecruitDaoImpl implements RecruitDao{

	private final SqlSession sqlSession;
	
	@Autowired
	public RecruitDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	@Override
	public Integer selectUserByNameAndPhone(RecruitVo recruitVo) throws Exception {
		return this.sqlSession.selectOne("recruit.validateUser", recruitVo);
	}

	@Override
	public Integer mergeRecruit(RecruitVo recruitVo) throws Exception {
		return this.sqlSession.insert("recruit.mergeRecruit", recruitVo);
	}
	
	@Override
	public RecruitVo selectUser(RecruitVo recruitVo) throws Exception {
		return this.sqlSession.selectOne("recruit.selectUser", recruitVo);
	}
	
	@Override
	public Integer mergeEducation(EducationVo educationVo) throws Exception {
		return this.sqlSession.insert("recruit.mergeEducation", educationVo);
	}
	
	@Override
	public Integer mergeCareer(CareerVo careerVo) throws Exception {
		return this.sqlSession.insert("recruit.mergeCareer", careerVo);
	}

	@Override
	public Integer mergeCertificate(CertificateVo certificateVo) throws Exception {
		return this.sqlSession.insert("recruit.mergeCertificate", certificateVo);
	}

	@Override
	public Integer deleteEducation(String eduSeq) throws Exception {
		return this.sqlSession.delete("recruit.deleteEducation", eduSeq);
	}

	@Override
	public List<EducationVo> selectEducationDetail(String seq) throws Exception {
		return this.sqlSession.selectList("recruit.selectEducationDetail", seq);
	}

	@Override
	public List<CareerVo> selectCareerDetail(String seq) throws Exception {
		return this.sqlSession.selectList("recruit.selectCareerDetail", seq);
	}

	@Override
	public List<CertificateVo> selectCertificateDetail(String seq) throws Exception {
		return this.sqlSession.selectList("recruit.selectCertificateDetail", seq);
	}

	@Override
	public Integer deleteCareer(String carSeq) throws Exception {
		return this.sqlSession.delete("recruit.deleteCareer", carSeq);
	}

	@Override
	public Integer deleteCertificate(String certSeq) throws Exception {
		return this.sqlSession.delete("recruit.deleteCertificate", certSeq);
	}

	@Override
	public UserSaveInfo selectUserInfoEducationDetail(String seq) throws Exception {
		return this.sqlSession.selectOne("recruit.selectUserInfoEducationDetail", seq);
	}

	@Override
	public UserSaveInfo selectUserInfoCareerDetail(String seq) throws Exception {
		return this.sqlSession.selectOne("recruit.selectUserInfoCareerDetail", seq);
	}


}
