package com.kh.board.model.service;

import java.sql.Connection;
import java.util.List;

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
		// ↑ bd.getBoardWriter() 안에 들어있는 "아이디(문자열)"로 MEMBER 테이블을 조회
		//있으면 Member 객체, 없으면 null
		if(member != null) {					// ← 왜 꼭 != null?
			int userNo = member.getUserNo();	// 	  null이면 getUserNo()에서 NPE 터짐
			// 3. 데이터 가공  / 생성				//    또한, 존재하지 않는 회원이면 FK 위반(ORA-02291)로 INSERT 실패 ==> 존재하는 회원인지 확인 → NPE 방지 + FK 무결성 보장.
			Board board = new Board(0,					// 게시글 PK는 DB에서 시퀀스(SEQ_BNO.NEXTVAL) 로 생성될 예정 // “아직 미발급” 상태를 의미하는 플레이스홀더로 0을 넣음
									  bd.getBoardTitle(),
									  bd.getBoardContent(),
									  String.valueOf(userNo), // (작성자 필드 타입이 String으로 설계되어있어 DB의 숫자키 (USERNO)를 문자열로 임시 변환 , int 자료형을 String으로 바꾸고 싶을 경우
									  null,					  // 만약 , 다시 설계한다면?  DB 컬럼 BOARD_WRITER가 NUMBER(= USERNO FK)라면, 도메인 모델도 int/Integer로 맞추는 게 베스트
									  null);
			
		  result = new BoardDAO().insertBoard(conn, board);
		}
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public List<Board> selectBoardList(){
		
		List<Board> boards = new BoardDAO().selectBoardList(conn);
		
		JDBCTemplate.close(conn);
		
		return boards;
	}
	
	public Board selectBoard(int boardNo) {
		
		// boardNo 시퀀스 가지고 만듦.... => 1부터 시작, 숫자 0 이하값 들어오면 갈 필요 없음
		// DB가면 돈 듦... => if 조건절 사용하기.
		
		Board board = null;
		
		if(boardNo > 0) {
			board = new BoardDAO().selectBoard(conn, boardNo);
		}
		
		JDBCTemplate.close(conn);
		
		return board;
	}
	
	public int deleteBoard(int boardNo) {
		int result = new BoardDAO().deleteBoard(conn, boardNo);
		
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return result;
	}
	
}
