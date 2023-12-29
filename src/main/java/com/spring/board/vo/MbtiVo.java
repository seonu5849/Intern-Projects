package com.spring.board.vo;

public class MbtiVo {

	private String type;
	private Integer questionNum;
	private Integer optionValue;
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getQuestionNum() {
		return questionNum;
	}
	public void setQuestionNum(Integer questionNum) {
		this.questionNum = questionNum;
	}
	public Integer getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(Integer optionValue) {
		this.optionValue = optionValue;
	}
	
	@Override
	public String toString() {
		return "type: "+this.type+", questionNum: "+this.questionNum+", optionValue: "+this.optionValue;
	}
	
	
	
	
}
