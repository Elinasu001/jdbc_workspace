package com.kh.statement.view;

import java.util.Scanner;

import com.kh.statement.controller.MemberController;

/**
 * MmeberView 클래스는 JDBC 실습을 위해 생성하였으며,
 * 사용자에게 입력 및 출력을 수행하는 메소드를 제공합니다.
 * 
 * @author 홍길동
 * @version 0.1
 * @date 2025-09-01
 */
public class MemberView {
	// MemberView 클래스 안 멤버변수(필드) Scanner와 MemberController를 필드로 포함
	private Scanner sc = new Scanner(System.in);
	private MemberController mc = new MemberController();
	
	/**
	 * 프로그램 구동 시 사용자에게 메인화면을 출력해주는 메소드입니다.
	 */
	public void mainMenu() {
		while(true) {
			System.out.println("-------회원 관리 프로그램--------");
			System.out.println("1. 회원 추가");
			System.out.println("2. 회원 전체 조회");
			System.out.println("3. 회원 아이디로 조회");
			System.out.println("4. 회원 이름 키워드로 조회");
			System.out.println("5. 회원 이름 정보 변경");
			System.out.println("6. 회원 탈퇴");
			System.out.println("9. 프로그램 종료");
			System.out.print("메뉴를 선택해주세요 > ");
			int menuNo = sc.nextInt();
			sc.nextLine();
			
			switch(menuNo) {
			case 1 : save(); break;
			case 2 : break;
			case 3 : break;
			case 4 : break;
			case 5 : break;
			case 6 : break;
			case 9 : System.out.println("프로그램을 종료합니다."); return;
			default : System.out.println("잘못된 메뉴 선택입니다.");
			}
			
		}
	}
	
	/**
	 * MEMBER 테이블에 INSERT할 값을 사용자가 입력받는 화면을 출력해주는 메소드
	 *
	 * 컬럼에 INSERT할 값들을 모두 입력받은 후 입력받은 값 컨트롤러로 전달
	 */
	private void save() {
		
		System.out.println("--- 회원 추가 ---");
		System.out.print("아이디를 입력해주세요 > ");
		String userId = sc.nextLine();
		// 원래는 여기다가 if로 길이 15가 넘으면 조건 달아줘야됨.
		System.out.println("비밀번호를 입력해주세요 > ");
		String userPwd = sc.nextLine();
		System.out.println("성함을 입력해주세요 > ");
		String userName = sc.nextLine();
		System.out.println("이메일을 입력해주세요 > ");
		String email = sc.nextLine();
		
		// 일단 View에서 할 일은 끝남 -> 컨트롤러로 요청 처리
		int result = mc.save(userId, userPwd, userName, email);
		
		// Controller 에서 받은 반환값으로 결과 출력
		if(result > 0) {
			System.out.println("회원 가입에 성공했습니다.");
		} else {
			System.out.println("회원 가입에 실패했습니다.");
		}
	}
	
}
