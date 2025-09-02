package com.kh.statement.controller;

import java.util.List;

import com.kh.statement.model.dao.MemberDao;
import com.kh.statement.model.dto.PasswordDTO;
import com.kh.statement.model.vo.Member;

/**
 * VIEW 에서 넘어온 요청을 처리해주는 클래스입니다.
 * 1. 메소드로 전달된 데이터를 가공처리한 뒤 DAO로 전달합니다.
 * 2. DAO로부터 반환받은 결과에 따라서 VIEW로 응답결과를 반환
 */
public class MemberController {
	
	/**
	 * VIEW로 부터 전달받은 문자열값 다섯개를 DAO로 전달하는 메소드
	 * 
	 * @param userId	: 사용자에게 입력받은 아이디 값 (나중에~ 조건 붙여주면 좋음)
	 * @param userPwd	: 사용자에게 입력받은 비밀번호 값
	 * @param userName	: 사용자에게 입력받은 이름 값
	 * @param email		: 사용자에게 입력받은 이메일 값
	 * 
	 * @return : 반환 어떻게 할건지
	 * @throws : IllgalArgumentException :	잘못된 인자가 전달되면 발생할 수 있음
	 */
	public int save(String userId, String userPwd, String userName, String email) {
		// 1. 데이터 가공 => 넘어온 인자값이 두 개 이상일 시	"잠시" 
		// 				  어딘가에 담을 것(VO, Map, DTO)
		// 전달된 인자값들을 Member 객체의 필드에 담기! (ArrayList x)
		// 1) 매개변수 생성자를 호출하여 객체 생성과 동시에 필드값을 대입하는 방법
		// 2) 기본생성자로 객체를 생성한 뒤 setter 메소드를 호출하는 방법
		Member member = new Member(userId, userPwd, userName, email);
		
		// 컨트롤러의 1절 끝
		int result = new MemberDao().save(member);
		
		// 2. 요청 처리 후 결과값 반환
		return result;
	}
	
	public List<Member> findAll() {
		
		// 1. 데이터 가공 == 할 게 없음
		// 2. DAO 호출
		List<Member> members = new MemberDao().findAll();
		
		return members;
	}
	
	/**
	 * 사용자에게 입력받은 아이디 값을 이용해서 검색요청을 처리해주는 메소드
	 * 
	 * @param userId : 사용자가 입력한 검색하고자하는 문자열
	 */
	public Member findById(String userId) {
		
		// 1. 데이터 가공 => 넘어가기
		// 2. 요청처리 => DAO 객체 생성 후 메소드 호출
		Member member = new MemberDao().findById(userId);
		
		// 3. 요청 처리 후 결과 반환
		return member;
	}
	
	
	/**
	 * @param keyword
	 * 
	 */
	public List<Member> findByKeyword(String keyword) {
		
		// 결과값이 나중에 어떻게 돌아올까??
		// SELECT -> ResultSet -> Member -> List<Member>
		List<Member> members = new MemberDao().findByKeyword(keyword);
		
		return members;
	}
	
	/**
	 * @param userId
	 * @param userPwd
	 * @param newPassword
	 */
	public int update(String userId, String userPwd, String newPassword) {
		
		// 1. 데이터 가공
		// DTO에 새로운 값들을 담아주자 !!
		PasswordDTO pd = new PasswordDTO(userId, userPwd, newPassword);
		
		// 2. 요청처리
		// 아이디랑 비밀번호랑 바꿀비밀번호 줄테니
		// 아이디랑 비밀번호 맞는 지 확인하고 바꿀 비밀번호로 비밀번호 컬럼값 바꿔줘
		// DAO야 이거 줄게 해줘
		int result = new MemberDao().update(pd);
		
		// 3. 뷰로 결과반환
		return result;
	}
	
	
	public int delete(String userId, String userPwd) {
		
		// 1. 데이터 가공
		Member member = new Member();
		// 생성자 없으니 setter로 넣기
		member.setUserId(userId);
		member.setUserPwd(userPwd);
		
		// 2. 요청 처리
		int result = new MemberDao().delete(member);
		
		// 3. 결과반환
		return result;
	}
	
}
