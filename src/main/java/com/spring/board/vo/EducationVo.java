package com.spring.board.vo;

public class EducationVo {
	
	private String eduSeq;
	private String seq;
	private String schoolName;
	private String division;
	private String eduStartPeriod;
	private String eduEndPeriod;
	private String major;
	private String grade;
	private String schoolLocation;
	
	
	public String getEduSeq() {
		return eduSeq;
	}

	public void setEduSeq(String eduSeq) {
		this.eduSeq = eduSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getDivision() {
		return division;
	}

	public void setDivision(String division) {
		this.division = division;
	}

	public String getEduStartPeriod() {
		return eduStartPeriod;
	}

	public void setEduStartPeriod(String eduStartPeriod) {
		this.eduStartPeriod = eduStartPeriod;
	}

	public String getEduEndPeriod() {
		return eduEndPeriod;
	}

	public void setEduEndPeriod(String eduEndPeriod) {
		this.eduEndPeriod = eduEndPeriod;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getSchoolLocation() {
		return schoolLocation;
	}

	public void setSchoolLocation(String schoolLocation) {
		this.schoolLocation = schoolLocation;
	}

	@Override
    public String toString() {
        return "eduSeq: " + eduSeq +
                ", seq: " + seq +
                ", schoolName: " + schoolName +
                ", division: " + division +
                ", eduStartPeriod: " + eduStartPeriod +
                ", eduEndPeriod: " + eduEndPeriod +
                ", major: " + major +
                ", grade: " + grade +
                ", eduLocation: " + schoolLocation;
    }
	
}
