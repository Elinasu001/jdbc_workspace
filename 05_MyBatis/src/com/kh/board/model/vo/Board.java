package com.kh.board.model.vo;

import java.sql.Date;
import java.util.Objects;


public class Board {
	private int boardNo;			// BOARD_NO NUMBER
	private String boardTitle;		// BOARD_TITLE VARCHAR2
	private String boardContent;	// BOARD_CONTENT VARCHAR2
	private String boardWriter;		// BOARD_WRITER NUMBER FOREIGN KEY(USERNO) -- insert 할 경우 int select 할 경우 string 파싱 하면됨
	private Date createDate;		// CREATE_DATE DATE
	private String deleteStatus;	// DELETE_STATUS CHAR(1)
	
	// 모든 필드에대한 매개변수 생성자 (기본생성자 x  x=> why? 불변을 지키기 위함)
	public Board(int boardNo, String boardTitle, String boardContent, String boardWriter, Date createDate,
			String deleteStatus) {
		super();
		this.boardNo = boardNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardWriter = boardWriter;
		this.createDate = createDate;
		this.deleteStatus = deleteStatus;
	}
	// getter // (setter x  x=> why? 불변을 지키기 위함)
	public int getBoardNo() {
		return boardNo;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public String getDeleteStatus() {
		return deleteStatus;
	}
	
	// equals, hashCode
	@Override
	public int hashCode() {
		return Objects.hash(boardContent, boardNo, boardTitle, boardWriter, createDate, deleteStatus);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Board other = (Board) obj;
		return Objects.equals(boardContent, other.boardContent) && boardNo == other.boardNo
				&& Objects.equals(boardTitle, other.boardTitle) && Objects.equals(boardWriter, other.boardWriter)
				&& Objects.equals(createDate, other.createDate) && Objects.equals(deleteStatus, other.deleteStatus);
	}


}






