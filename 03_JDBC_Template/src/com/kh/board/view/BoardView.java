package com.kh.board.view;

import java.util.List;
import java.util.Scanner;

import com.kh.board.controller.BoardController;
import com.kh.board.model.dto.BoardDTO;
import com.kh.board.model.vo.Board;

/*
 * 일반적으로 CRUD 작업의 메소드를 지을 때 메소드명 예시!)
 * SELECT / INSERT / UPDATE / DELETE
 * 
 * [공공기관]
 * BOARD를 예시로 들어보겠음
 * INSERT : insertBoard()
 * UPDATE : updateBoard()
 * DELETE : deleteBoard()
 * SELECT(다수행) : selectBoardList()
 * SELECT(단일행) : selectBoard()
 * --------------------------------
 * [회사]
 * INSERT : save()
 * UPDATE : update()
 * DELETE : deleteByXXX()
 * SELECT(다수행) : findAll()
 * SELECT(단일행) : findByXXX()
 */






public class BoardView {
	private Scanner sc = new Scanner(System.in);
	private BoardController bc = new BoardController();
	
	public void mainMenu() {
		while(true) {
			System.out.println("게시판 서비스입니다!");
			// 전체 게시글 목록 조회
			
			selectBoardList();
			
			System.out.println("\n=========================================");
			System.out.println("1. 게시글 상세조회");
			System.out.println("2. 게시글 작성하기");
			System.out.println("3. 게시글 삭제하기");
			System.out.println("9. 회원 메뉴로 돌가가기");
			System.out.print("메뉴를 선택해주세요 > ");
			
			int menuNo = sc.nextInt();
			sc.nextLine();
			
			switch(menuNo) {
			case 1: selectBoard(); break;
			case 2: insertBoard(); break;
			case 3: break;
			case 9: System.out.println("잘가시오~"); break;
			}
		}
	}
	
	private void insertBoard() {
		
		System.out.println("게시글 작성 서비스입니다!");
		
		System.out.println("아이디를 입력해주세요 > ");
		String userId = sc.nextLine();
		
		System.out.println("제목을 입력해주세요 > ");
		String boardTitle = sc.nextLine();
		
		System.out.println("본문을 입력해주세요 > ");
		String boardContent = sc.nextLine();
		
		// 뷰 1절 끝
		int result = bc.insertBoard(new BoardDTO(boardTitle, boardContent, userId));
		
		if(result > 0) {
			System.out.println("게시글 작성 성공!");
		} else {
			System.out.println("게시글 작성 실패 ");
		}
		System.out.println();
		System.out.println();
		
	}
	
	private void selectBoardList() {
		System.out.println();
		
		List<Board> boards = bc.selectBoardList();
		
		if(!boards.isEmpty()) {
			// 향상된 for문 사용 가능
			boards.stream().map(b -> "\n게시글번호 : " + b.getBoardNo()
								  + "\t제목 : " + b.getBoardTitle()
								  +"\t\t작성자 : " + b.getBoardWriter()
								  +"\t작성일 : " + b.getCreateDate())
						   .forEach(System.out::print);
			
			
		} else {
			System.out.println("게시글이 존재하지 않습니다.");
			System.out.println("첫 게시글의 주인공이 되어보세요!");
		}
	}
	
	
	private void selectBoard() {
		System.out.println("게 시 글 상 세 조 회 서 비 스 입 니 다.");
		System.out.println("조회할 게시글 번호를 입력하세요 > ");
		int boardNo = sc.nextInt();
		sc.nextLine();
		
		// 하나 들고옴
		Board board = bc.selectBoard(boardNo);
		
		if(board != null) {
			// 하나만 조회 되는 거라 반복문 필요 없음.
			System.out.println("\n\n 제목 : " + board.getBoardTitle());
			System.out.println("\n\n 작성자 : " + board.getBoardWriter());
			System.out.println("\n\n 작성일 : " + board.getCreateDate());
			System.out.println("\n\n 본문 : " + board.getBoardContent());
			System.out.println("----------------------------------------");
			System.out.println(board.getBoardContent());
			System.out.println("----------------------------------------");
			
		} else {
			System.out.println("존재하지 않는 게시글 번호입니다.");
		}
		
		while(true) {
			System.out.println("목록으로 돌아가실려면 돌아가기를 입력하세요.");
			String exit = sc.nextLine();
			if("돌아가기".equals(exit)) {
				return;
			}
		}
		
	}
	
	
	
}
