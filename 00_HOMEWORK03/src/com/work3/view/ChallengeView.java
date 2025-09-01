package com.work3.view;

import java.util.List;
import java.util.Scanner;

import com.work3.controller.ChallengeController;
import com.work3.model.vo.Challenge;

public class ChallengeView {
	private Scanner sc = new Scanner(System.in);
	private ChallengeController cc = new ChallengeController();
	
	public void mainMenu() {
		// 전체 조회
		// 유니크 들어가 있는 아이디 컬럼 조회
		// like 키워드 제목 조회
		while(true) {
			System.out.println("----------- 챌린지 서비스 ----------");
			System.out.println("1. 챌린지 추가");
			System.out.println("2. 챌린지 전체 조회");
			System.out.println("3. 챌린지 아이디로 조회");
			System.out.println("4. 챌린지 제목 키워드로 조회");
			System.out.println("5. 챌린지 정보 변경");
			System.out.println("6. 챌린지 삭제");
			System.out.println("9. 프로그램 종료하기");
			System.out.println("번호를 선택해주세요 > ");
			int menuNo = sc.nextInt();
			sc.nextLine();
			
			switch(menuNo) {
			case 1 : save(); break;
			case 2 : findAll(); break;
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
	 * CHALLENGE 테이블에 INSERT할 값을 사용자가 입력받는 화면을 출력해주는 메소드
	 * 
	 * 컬럼에 INSERT할 값들을 모두 입력받은 후 입력받은 값 컨트롤러로 전달
	 */
	private void save() {
		
		System.out.println("--- 챌린지 추가 서비스 입니다 ---");
		System.out.println("챌린지 아이디를 입력해주세요 > ");
		String challengeId = sc.nextLine();
		System.out.println("챌린지 제목을 입력해주세요 > ");
		String title = sc.nextLine();
		System.out.println("챌린지 설명을 작성해주세요 > ");
		String desc = sc.nextLine();
		System.out.println("챌린지 시작날짜를 설정해주세요 > ");
		String startDate = sc.nextLine();
		System.out.println("챌린지 종료일날짜를 설정해주세요 > ");
		String endDate = sc.nextLine();
		System.out.println("보상 포인트를 설정해주세요 > ");
		int rewardPoint = sc.nextInt();
		sc.nextLine();
		System.out.println("생성자 유저 번호를 입력해주세요 > ");
		int creatorUserNo = sc.nextInt();
		sc.nextLine();
		
		int result = cc.save(challengeId, title, desc, startDate, endDate, rewardPoint, creatorUserNo);
		
		if(result > 0) {
			System.out.println("챌린지 추가에 성공했습니다.");
		}else {
			System.out.println("챌린지 추가에 실패했습니다.");
		}
	}
	
	private void findAll() {
		System.out.println("\n챌린지 전체 조회");
		
		List<Challenge> challenges = cc.findAll();
		// 뷰에서 2절
		System.out.println("\n조회된 총 회원수는 " + challenges.size() + "명 입니다.");
		if(challenges.isEmpty()) {
			System.out.println("조회결과가 존재하지 않습니다.");
		}else {
			challenges.stream().forEach(challenge -> {
			    System.out.println("===============================");
			    System.out.print("챌린지 아이디 : " + challenge.getChallengeId() + ", ");
			    System.out.print("챌린지 제목 : " + challenge.getTitle() + ", ");
			    System.out.print("설명 : " + challenge.getTitle() + ", ");
			    System.out.print("시작일 : " + challenge.getStartDate() + ", ");
			    System.out.print("종료일 : " + challenge.getEndDate()+ ", ");
			    System.out.print("생성자 : " + challenge.getCreatorUserNo());
			    System.out.println();
			});
			
		}
	}
}
