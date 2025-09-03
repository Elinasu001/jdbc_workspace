package com.kh.board.controller;

import java.util.List;

import com.kh.board.model.dto.BoardDTO;
import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;

public class BoardController {

	public int insertBoard(BoardDTO bd) {
		
		//return new BoardService().insertBoard(bd); 도 가능
		int result = new BoardService().insertBoard(bd);
		
		return result;
	}
	
	public List<Board> selectBoardList(){
		
		List<Board> boards = new BoardService().selectBoardList();
		
		return boards;
	}
	
	public Board selectBoard(int boardNo) {
		
		return new BoardService().selectBoard(boardNo);
	
	}
	
	public int deleteBoard(int boardNo) {
		return new BoardService().deleteBoard(boardNo);
	}
	
	
}
