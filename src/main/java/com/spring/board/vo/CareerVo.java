package com.spring.board.vo;

public class CareerVo {

	private String carSeq;
	private String seq;
	private String compName;
	private String jobLocation;
	private String jobStartPeriod;
	private String jobEndPeriod;
	private String task;
	private String salary;
	
	public String getCarSeq() {
		return carSeq;
	}

	public void setCarSeq(String carSeq) {
		this.carSeq = carSeq;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public String getJobLocation() {
		return jobLocation;
	}

	public void setJobLocation(String jobLocation) {
		this.jobLocation = jobLocation;
	}

	public String getJobStartPeriod() {
		return jobStartPeriod;
	}

	public void setJobStartPeriod(String jobStartPeriod) {
		this.jobStartPeriod = jobStartPeriod;
	}

	public String getJobEndPeriod() {
		return jobEndPeriod;
	}

	public void setJobEndPeriod(String jobEndPeriod) {
		this.jobEndPeriod = jobEndPeriod;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	@Override
    public String toString() {
        return "carSeq: " + carSeq +
                ", seq: " + seq +
                ", compName: " + compName +
                ", location: " + jobLocation +
                ", startPeriod: " + jobStartPeriod +
                ", endPeriod: " + jobEndPeriod +
                ", task: " + task +
                ", salary: " + salary;
    }
	
}
