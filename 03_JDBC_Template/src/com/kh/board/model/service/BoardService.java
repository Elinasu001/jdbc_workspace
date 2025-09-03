package com.kh.board.model.service;

import java.sql.Connection;

import com.kh.board.model.dao.BoardDAO;
import com.kh.board.model.dto.BoardDTO;
import com.kh.board.model.vo.Board;
import com.kh.common.JDBCTemplate;
import com.kh.statement.model.dao.MemberDao;
import com.kh.statement.model.vo.Member;

public class BoardService {
	private Connection conn = null;
	
	public BoardService() {
		conn = JDBCTemplate.getConnection();
	}
	
	
	public int insertBoard(BoardDTO bd) {
		// 내가 입력한 값을 가지고
		// BOARD테이블에 한 행 INSERT 해줘 !
		
		int result = 0;
		
		// 1. 값의 유효성 검증
		if("".equals(bd.getBoardTitle().trim())){
			return result;
		}
		
		
		// 제목: 안녕하세요, 내용 : 반갑습니다, 아이디 : admin  ==> INSERT하기 전에 아이디가 있는지 봐야 하니 DAO 에서 SELECT 해야함
		// 2. 인증 / 인가
		///new BoardDAO().searchId(conn, bd.getBoardWriter()); 안됨 why? 아이디를 가지고 회원 조회 하는 건 이미 MemberDao에 만들어 놨음
		Member member = new MemberDao().findById(conn, bd.getBoardWriter()); // 있으면 주소값 담기고 없으면 null 
		
		if(member != null) {
			int userNo = member.getUserNo();
			// 3. 데이터 가공  / 생성
			Board board = new Board(0,
									  bd.getBoardTitle(),
									  bd.getBoardContent(),
									  String.valueOf(userNo), // int 자료형을 String으로 바꾸고 싶을 경우
									  null,
									  null);
			
		  result = new BoardDAO().insertBoard(conn, board);
		}
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
}
