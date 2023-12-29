package com.spring.board.vo;

import java.util.List;

public class RecruitVo {
	
	private String seq; 
	private String name; 		// 이름
	private String birth;		// 생년월일
	private String gender;		// 성별
	private String phone;		// 연락처
	private String email;		// 이메일
	private String addr;		// 주소
	private String hopeLocation;	// 희망근무지
	private String workType;	// 근무형태
	private String submit;		// 제출여부
	
	// 외부 객체
	private List<EducationVo> educationVo;
	private List<CareerVo> careerVo;
	private List<CertificateVo> certificateVo;
	
	
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
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getHopeLocation() {
		return hopeLocation;
	}
	public void setHopeLocation(String location) {
		this.hopeLocation = location;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getSubmit() {
		return submit;
	}
	public void setSubmit(String submit) {
		this.submit = submit;
	}
	
	@Override
    public String toString() {
        return "seq: " + seq +
                ", name: " + name +
                ", birth: " + birth +
                ", gender: " + gender +
                ", phone: " + phone +
                ", email: " + email +
                ", addr: " + addr +
                ", location: " + hopeLocation +
                ", workType: " + workType +
                ", submit: " + submit +
                ", educationVo: " + educationVo +
                ", careerVo: " + careerVo +
                ", certificateVo: " + certificateVo;
    }

}
