package com.spring.board.vo;

import java.util.List;

public class RecruitTotalInputData {

	private RecruitVo recruitVo;
	private List<EducationVo> educationVo;
	private List<CareerVo> careerVo;
	private List<CertificateVo> certificateVo;
	
	public RecruitVo getRecruitVo() {
		return recruitVo;
	}
	public void setRecruitVo(RecruitVo recruitVo) {
		this.recruitVo = recruitVo;
	}
	public List<EducationVo> getEducationVo() {
		return educationVo;
	}
	public void setEducationVo(List<EducationVo> educationVo) {
		this.educationVo = educationVo;
	}
	public List<CareerVo> getCareerVo() {
		return careerVo;
	}
	public void setCareerVo(List<CareerVo> careerVo) {
		this.careerVo = careerVo;
	}
	public List<CertificateVo> getCertificateVo() {
		return certificateVo;
	}
	public void setCertificateVo(List<CertificateVo> certificateVo) {
		this.certificateVo = certificateVo;
	}

	@Override
    public String toString() {
        return "RecruitTotalInputData{" +
                "recruitVo=" + recruitVo +
                ", educationVo=" + educationVo +
                ", careerVo=" + careerVo +
                ", certificateVo=" + certificateVo +
                '}';
    }
	
}
