package com.kh.board.model.dao;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public void outputHTML(Connection conn) {
		
		FileWriter fos = null;
		BufferedWriter bw = null;
		
		try {
			fos = new FileWriter("Template_BOARD.html");
			bw = new BufferedWriter(fos);
			List<Board> boardList = selectBoardList(conn);
			String html = "<!DOCTYPE html>";
			html += "<html>";
			html += "<head><title>게시판이예용</title>";
			html += "<link rel='stylesheet' href='https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css'>";
			html += "<script src='https://cdn.jsdelivr.net/npm/jquery@3.7.1/dist/jquery.slim.min.js'></script>";
			html += "<script src='https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js'></script>";
			html += "<script src='https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js'></script>";
			html += "</head>";
			html += "<body>";
			html += "<h1 style='margin-bottom:30px; text-align:center'>JDBC 게시판 서비스입니다</h1>";
			html += "<table class='table'>";
			html += "<tr><th>제목</th><th>작성자</th><th>작성일</th></tr>";
			for(Board b : boardList) {
				html += "<tr>";
				html += "<td>" + b.getBoardTitle() + "</td>";
				html += "<td>" + b.getBoardWriter() + "</td>";
				html += "<td>" + b.getCreateDate() + "</td>";
				html += "</tr>";
			}
			html += "</table>";
			html += "</body>";
			html += "</html>";
			
			bw.write(html);
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	
}
