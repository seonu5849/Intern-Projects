package com.spring.board.vo;

public class UserSaveInfo {

	private String schoolName;
	private String schoolYear;
	private String division;
	private String jobYear;
	private String jobMonth;
	private String salary;
	
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getDivision() {
		return division;
	}
	public void setDivision(String division) {
		this.division = division;
	}
	public String getJobYear() {
		return jobYear;
	}
	public void setJobYear(String jobYear) {
		this.jobYear = jobYear;
	}
	public String getJobMonth() {
		return jobMonth;
	}
	public void setJobMonth(String joMonth) {
		this.jobMonth = joMonth;
	}
	public String getSalary() {
		return salary;
	}
	public void setSalary(String salary) {
		this.salary = salary;
	}
	
	@Override
    public String toString() {
        return "UserSubmitInfo{" +
                "schoolName='" + schoolName + '\'' +
                ", schoolYear='" + schoolYear + '\'' +
                ", division='" + division + '\'' +
                ", jobYear='" + jobYear + '\'' +
                ", jobMonth='" + jobMonth + '\'' +
                ", salary='" + salary + '\'' +
                '}';
    }
	
}
