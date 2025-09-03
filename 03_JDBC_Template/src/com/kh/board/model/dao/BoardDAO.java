package com.kh.board.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
	
	public List<Board> selectBoardList(Connection conn){
		
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		List<Board> boards = new ArrayList();
		
		// ORACLE 구문
		String sql = """
						SELECT
						       BOARD_NO
						     , BOARD_TITLE
						     , USERID
						     , CREATE_DATE
						  FROM
						       BOARD
						     , MEMBER
						 WHERE
						       BOARD_WRITER = USERNO
						   AND
						       DELETE_YN = 'N'
						 ORDER
						    BY
						       BOARD_NO DESC
				     """;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Board board = new Board(rset.getInt("BOARD_NO")
						               ,rset.getString("BOARD_TITLE")
						               ,null
						               ,rset.getString("USERID")
						               ,rset.getDate("CREATE_DATE")
						               ,null);
				boards.add(board);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return boards;
	}
	
	public Board selectBoard(Connection conn, int boardNo) {
		Board board = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//ANSI 구문
		String sql = """
						SELECT
						       BOARD_NO
						     , BOARD_TITLE
						     , BOARD_CONTENT
						     , USERID
						     , CREATE_DATE
						     , DELETE_YN
				  		  FROM
				  		  	   BOARD
				  		  JOIN
						       MEMBER ON (USERNO = BOARD_WRITER)
						 WHERE
						       DELETE_YN = 'N'
						   AND
						       BOARD_NO = ?
					 """;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, boardNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new Board(rset.getInt("BOARD_NO")
						         ,rset.getString("BOARD_TITLE")
						         ,rset.getString("BOARD_CONTENT")
						         ,rset.getString("USERID")
						         ,rset.getDate("CREATE_DATE")
						         ,rset.getString("DELETE_YN"));
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return board;
	}
	
	public int deleteBoard(Connection conn, int boardNo) {
		

		try(PreparedStatement pstmt = conn.prepareStatement("""
																UPDATE
																       BOARD
																   SET
																       DELETE_YN = 'Y'
																 WHERE
																       BOARD_NO = ?
															""")){
			
			pstmt.setInt(1, boardNo);
			return pstmt.executeUpdate();
	} catch(SQLException e) {
		e.printStackTrace();
	}
		
	return 0;
	}
	
	
}
