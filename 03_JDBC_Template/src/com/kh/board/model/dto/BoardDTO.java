package com.kh.board.model.dto;

import java.sql.Date;

public class BoardDTO {
	private int boardNo;			// BOARD_NO NUMBER
	private String boardTitle;		// BOARD_TITLE VARCHAR2
	private String boardContent;	// BOARD_CONTENT VARCHAR2
	private String boardWriter;		// BOARD_WRITER NUMBER FOREIGN KEY(USERNO) -- insert 할 경우 int select 할 경우 string 파싱 하면됨
	private Date createDate;		// CREATE_DATE DATE
	private String deleteStatus;	// DELETE_STATUS CHAR(1)
	
	// 기본생성자, boardNo, createDate, deleteStatus 세 개 뺀 생성자, 모든 필드 생성자
	public BoardDTO() {
		super();
		
	}
	public BoardDTO(String boardTitle, String boardContent, String boardWriter) {
		super();
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
	}
	public BoardDTO(int boardNo, String boardTitle, String boardContent, String boardWriter, Date createDate,
			String deleteStatus) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.createDate = createDate;
		this.deleteStatus = deleteStatus;
	}
	
	// getter, setter
	public int getBoardNo() {
		return boardNo;
	}
	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	
}
