package com.spring.board.vo;

public class PageVo {
	
	private Integer pageNo = 0;
	private int perPage = 10;
	private Integer totalPage = 0;
	private final int pagesPerBlock = 5;
	
	private int totalBlocks; // 5개로 페이지번호를 나눴을때 나오는 블럭의 개수, 만약 6개라면 총 블록의 개수는 2개이다.
	private int currentBlock; // 현재 몇번 블럭에 있는지
	private int beginPageNo; // 현재 블럭의 시작번호는 몇번인지
	private int endPageNo; // 현재 블럭의 끝 번호는 몇번인지

	public Integer getPageNo() {
		return this.pageNo != null ? this.pageNo : 0;
	}
	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	
	public void setTotalPage(Integer totalBoardCnt) {
//		int remainder = totalBoardCnt % this.perPage;
//		int totalPage = 0;
//		
//		if(remainder != 0) {
//			totalPage = totalBoardCnt / this.perPage + 1;
//		}else {
//			totalPage = totalBoardCnt / this.perPage;
//		}
//		
		this.totalPage =  (int)(Math.ceil((double)totalBoardCnt/this.perPage));
	}

	public Integer getTotalPage() {
		return this.totalPage;
	}

	public int getTotalBlocks() {
		return totalBlocks;
	}

	public void setTotalBlocks(int totalPage) {
		this.totalBlocks = (int)(Math.ceil((double)totalPage / this.pagesPerBlock));
	}

	public int getCurrentBlock() {
		return currentBlock;
	}

	public void setCurrentBlock(int pageNo) {
		this.currentBlock = (int)(Math.ceil((double)pageNo / this.pagesPerBlock));
	}

	public int getBeginPageNo() {
		return (this.currentBlock - 1) * this.pagesPerBlock + 1;
	}

	public void setBeginPageNo(int beginPageNo) {
		this.beginPageNo = beginPageNo;
	}

	public int getEndPageNo() {
		return this.currentBlock * this.pagesPerBlock;
	}

	public void setEndPageNo(int endPageNo) {
		this.endPageNo = endPageNo;
	}

	public int getPerPage() {
		return this.perPage;
	}
	
}
