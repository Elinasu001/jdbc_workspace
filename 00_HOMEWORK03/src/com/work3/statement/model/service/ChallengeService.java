package com.work3.statement.model.service;

import static com.work3.common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.List;

import com.work3.common.JDBCTemplate;
import com.work3.statement.model.dao.ChallengeDao;
import com.work3.statement.model.dto.TitleDTO;
import com.work3.statement.model.vo.Challenge;

public class ChallengeService {
	
	private Connection conn = null;
	
	public ChallengeService() {
		this.conn = getConnection();
	}
	public int save(Challenge challenge) {
		
		// Connection 객체 생성
		//JDBCTemplate.getConnection();
		
		// DAO 호출 시 Connection 객체 전달 + Controller가 넘겨준 사용자 입력한 값이 필드에 담겨있는 Challenge 참조변수를 함께 넘겨준다.
		int result = new ChallengeDao().save(conn, challenge);
		
		// 트랜잭션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		}
		
		// 자원 반납
		JDBCTemplate.close(conn);
		
		// Controller로 결과 반환
		return result;
	}
	
	public List<Challenge> findAll(){
		
		// 객체 생성
		//JDBCTemplate.getConnection();
		
		// DAO 호출
		List<Challenge> challenges = new ChallengeDao().findAll(conn);
		
		// 자원 반납
		JDBCTemplate.close(conn);
		
		// Controller로 결과 반환
		return challenges;
	}
	
	public Challenge findById(String challengeId) {
		
		// 객체 생성
		//JDBCTemplate.getConnection();
		
		// DAO호출
		Challenge challenge = new ChallengeDao().findById(conn, challengeId);
		
		// 자원 반납
		JDBCTemplate.close(conn);
		
		// Controller로 결과 반환
		return challenge;
	}
	
	public List<Challenge> findByKeyword(String keyword){
		
		// 객체 생성
		//JDBCTemplate.getConnection();
		
		// DAO 호출
		List<Challenge> challenges = new ChallengeDao().findByKeyword(conn, keyword);
		
		// 자원 반납
		JDBCTemplate.close(conn);
		
		// Controller로 결과 반환
		return challenges;
	}
	
	
	public int update(TitleDTO td) {
		
		// 객체 생성
		//JDBCTemplate.getConnection();
		
		// 유효성 검증
		Challenge challenge = new ChallengeDao().findById(conn, td.getChallengeId());
		if(challenge == null) {
			return 0;
		}
		
		// DAO호출
		int result = new ChallengeDao().update(conn, td);
		
		// 트랜잭션 처리
		if(result > 0 ) {
			JDBCTemplate.commit(conn);
		}
		
		// 자원 반납
		JDBCTemplate.close(conn);
		
		// Controller로 결과 반환
		return result;
	}
	
	
	public int delete(Challenge challenge) {
		
		// 객체 생성
		JDBCTemplate.getConnection();
		
		// DAO 호출
		int result = new ChallengeDao().delete(conn, challenge);
	
		// 트랜잭션 처리
		if(result > 0) {
			JDBCTemplate.close(conn);
		}
		
		// Controller로 결과 반환
		return result;
	
	}
	
}
