package com.kh.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.kh.board.model.vo.Board;
import com.kh.common.JDBCTemplate;

public class BoardDAO {
	public int insertBoard(Connection conn, Board board) {
		// 0) 변수 선언
		PreparedStatement pstmt = null;
		int result = 0;
		String sql = """
						INSERT
						  INTO
						       BOARD
						       (
						       BOARD_NO
						     , BOARD_TITLE
						     , BOARD_CONTENT
						     , BOARD_WRITER
						       )
						VALUES
						       (
						       SEQ_BNO.NEXTVAL
						     , ?
						     , ?
						     , ?
						       )
					 """;
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, board.getBoardTitle());
			pstmt.setString(2, board.getBoardContent());
			pstmt.setInt(3, Integer.parseInt(board.getBoardWriter())); // 파싱  writer string 을 int이 userno 로 받아야 되기 때문
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
}
