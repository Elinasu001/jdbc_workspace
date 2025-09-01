package com.work3.view;

import java.util.Scanner;

import com.work3.controller.EventController;

public class EventView {
	private Scanner sc = new Scanner(System.in);
	private EventController ec = new EventController();
	
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
	
	private void save() {
		System.out.println("--- 챌린지 추가 서비스 입니다 ---");
		System.out.println("챌린지 아이디를 입력해주세요 > ");
		String eventId = sc.nextLine();
		System.out.println("챌린지 제목을 입력해주세요 > ");
		String title = sc.nextLine();
		System.out.println("챌린지 설명을 작성해주세요 > ");
		String desc = sc.nextLine();
		System.out.println("챌린지 시작날짜를 설정해주세요 > ");
		String startDate = sc.nextLine();
		System.out.println("챌린지 마지막날짜를 설정해주세요 > ");
		String endDate = sc.nextLine();
		System.out.println("보상 포인트를 설정해주세요 > ");
		int rewardPoint = sc.nextInt();
		sc.nextLine();
		
		int result = ec.save(eventId, title, desc, startDate, endDate, rewardPoint);
		
		if(result > 0) {
			
		}else {
			
		}
	}
}
