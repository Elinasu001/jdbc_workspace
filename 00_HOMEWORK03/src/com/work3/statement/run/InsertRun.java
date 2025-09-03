package com.work3.statement.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertRun {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		int rset = 0;
		
		Scanner sc = new Scanner(System.in);
		System.out.print("이벤트 아이디 작성해주세요 > ");
		String eventId = sc.nextLine();
		
		System.out.print("이벤트 제목 작성해주세요 > ");
		String title = sc.nextLine();
		
		System.out.print("이벤트 설명을 작성해주세요 > ");
		String desc = sc.nextLine();
		
		System.out.print("시작 날짜를 설정해주세요 > (예: 2025-09-07)");
		String startDate = sc.nextLine();
		
		System.out.print("끝나는 날짜를 설정해주세요 > (예: 2025-09-20)");
		String endDate = sc.nextLine();
		
		System.out.print("보상 포인트를 설정해주세요 > ");
		int rewardPoint = sc.nextInt();
		sc.nextLine();
		
		String sql = 
			    "INSERT INTO TB_EVENT (EVENT_NO, EVENT_ID, TITLE, DESCRIPTION, START_DATE, END_DATE, REWARD_POINT) " +
	    	    "VALUES (SEQ_EVENT.NEXTVAL, " +
			    "'" + eventId + "', " +
	    	    "'" + title + "', " +
	    	    "'" + desc + "', " +
	    	    "TO_DATE('" + startDate + "', 'YYYY-MM-DD'), " +
	    	    "TO_DATE('" + endDate   + "', 'YYYY-MM-DD'), " +
	    	    rewardPoint + ")";
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver 성공!");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			System.out.println("Driver 접속 성공!");
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			System.out.println("Statement 객체 생성!");
			
			rset = stmt.executeUpdate(sql);
			System.out.println("SQL문 실행 성공!");
			
			if(rset > 0) {
				conn.commit();
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				conn.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
