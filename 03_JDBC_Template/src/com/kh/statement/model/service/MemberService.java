package com.kh.statement.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.List;

import com.kh.common.JDBCTemplate;
import com.kh.statement.model.dao.MemberDao;
import com.kh.statement.model.dto.PasswordDTO;
import com.kh.statement.model.vo.Member;

// 클라이언트의 요청처리
// 제어흐름 --> 컨트롤러
// 핵심로직 실행 --> 서비스

/*
 * 비지니스 로직 실행(의사결정코드) -> 데이터 가공, 중복 체크, 연산 처리, 암호과
 * 트랜잭션 관리											-> 하나의 메소드는 하나의 sql문
 * 여러 DAO를 조합
 * 예외 처리 및 변환
 * 보안 및 권한 검사
 * 
 * 
 */

public class MemberService {
	//private MemberDao md = new MemberDao(); // 지금 필드로 두지는 않지만, 이런 방식으로 진행 할 경우 MemberService 죽는 동시에 MemberDao()도 죽일려면 이렇게 상단에 넣고 md = null; 을 메소드 안에 하나씩 넣어준다.
	private Connection conn = null; // 중복 사용 => public MemberService() 메소드를 이용해 생성 될때마다 getConnection()하면  Connection conn = getConnection();날려도 됨.
	
	/*
	 * Service : 비즈니스로직 / 의사결정코드를 작성하는 부분
	 * 			 Controller -> Service의 메소드를 호출
	 * 		     Service에서 Connection을 생성해서 DAO로 전달
	 * 			 만약, SQL문을 수행해야하는데 필요한 값이 있다면 Controller로부터 전달받아서
	 * 		     Connection과 같이 넘겨줄 것
	 * 			 DAO에서 DB작업이 끝나면 Service단에서 결과에 따른 트랜잭션 처리도 진행
	 * 			 
	 * 			 => Service를 추가함으로 DAO는 순수하게 SQL문을 처리하는 부분만 남겨둘 것
	 */
	
	public MemberService() {
		this.conn = getConnection();
	}
	
	//JDBCTemplate.regisDriver(); => 한번 실행 및 service와 관련된 메소드는 아니니 Run에 등록
	public int save(Member member) {
		
		// Connection 객체 생성
		Connection conn = JDBCTemplate.getConnection();
		
		// DAO 호출 시 Connection 객체 전달
		// +
		// Controller가 넘겨준 사용자가 입력한 값이 필드에 담겨있는
		// Member 참조변수를 함께 넘겨줌
		int result = new MemberDao().save(conn, member);
		
		// 6) 트랜잭션 처리
		if(result > 0) {
			JDBCTemplate.commit(conn);
		} 
		
		// 7_2) 자원반납
		JDBCTemplate.close(conn);
		
		return result;
	}
	
	public List<Member> findAll(){
		
		// 1) Connection 객체 생성
		Connection conn = getConnection();
		
		// 2) DAO호출해서 반환받기
		// Service에서 받아온 Connection 넘겨주기 + 만약에 Controller가 넘겨준 값이 있다면
		// 같이 넘겨줄 것
		List<Member> members = new MemberDao().findAll(conn);
		
		// 3) Connection 반납
		close(conn);
		
		// 4) 결과반환
		return members;
	
	}
	
	// 유니크 제약 조건 - 하나의 행은 하나의 vo객체의 필드값으로 다룸
	public Member findById(String userId) {
		
		// 1) Connection 객체 받아오기
		// Connection conn = getConnection(); 중복 사용으로 상단에 호출
		
		// 진짜 1) DAO호출(Service 생성자에서 받아온 Connection + Controller가 준 값)
		Member member = new MemberDao().findById(conn, userId);
		
		
		// 2) Connection 객체 반납
		close(conn);
		
		// 3) Controller에게 결과 반환
		return member;
	}
	
	public List<Member> findByKeyword(String keyword){
		// 할 일 !
		// 1) Connection 만들기 => 기본생성자에서 벌써 함!
		// 2) DAO 호출 !
		List<Member> members = new MemberDao().findByKeyword(conn, keyword);
		// 3) Connection 반납
		close(conn);
		// 4) 결과반환
		return members;
	}
	
	// 의사결정코드
	public int update(PasswordDTO pd) {
		// 회원의 비밀번호를 수정해야한다 == Member테이블에서 한 행 UPDATE
		// 비밀번호 수정
		// UPDATE MEMBER SET USERPWD = 머시기 WHERE USERID = 머시기 AND USERPWD = 머시기
		if(pd.getNewPassword().length() > 20) { // 굳이 DAO 갈 필요가 없기 때문에 여기서 처리
			//throw new RuntimeException("너무 긴 비밀번호입니다~~~."); //일단, 안배웠으니 return 0; 처리
			return 0;
		}
		Member member = new MemberDao().findById(conn, pd.getUserId());
		if(member == null) {
			//throw new RuntimeException("존재하지 않는 아이디입니다.");
			return 0;
		}
		int result = new MemberDao().update(conn, pd);
		if(result > 0) {
			commit(conn);
		}
		close(conn);
		
		return result;
	}
	
	public int delete(Member member) {
		// 회원의 정보를 삭제해야한다 == Member테이블에서 한 행 DELETE
		// 1) Connection 만들어야지
		
		// 2) 매개변수 받은거하고 커넥현사고 DAO로 넘겨야지
		int result = new MemberDao().delete(conn, member);
		// 3) DML이니깐 다녀오면 트랜잭션처리
		if(result > 0) {
			commit(conn);
		}
		// 4) 트랜잭션 끝나면 Connection 할 일 없으니깐 반납해야지
		close(conn);
		// 5) 결과 반환해줘야지
		return result;
	}
	
	

}
