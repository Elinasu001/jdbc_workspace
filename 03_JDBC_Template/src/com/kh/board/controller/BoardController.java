package com.kh.board.controller;

import com.kh.board.model.dto.BoardDTO;
import com.kh.board.model.service.BoardService;

public class BoardController {

	public int insertBoard(BoardDTO bd) {
		
		//return new BoardService().insertBoard(bd); 도 가능
		int result = new BoardService().insertBoard(bd);
		return result;
	}
	
}
