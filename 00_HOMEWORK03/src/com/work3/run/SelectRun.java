package com.work3.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SelectRun {

	public static void main(String[] args) {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rset = null;
		
		String sql = """
						SELECT
							   EVENT_NO
						     , EVENT_ID
						     , TITLE
						     , DESCRIPTION
						     , START_DATE
						     , END_DATE
						     , REWARD_POINT
						  FROM
						       TB_EVENT
						 ORDER
						    BY
						       START_DATE
					 		""";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			System.out.println("DB서버 접속 성공!");
			
			stmt = conn.createStatement();
			System.out.println("객체 생성 완료!");
			
			rset = stmt.executeQuery(sql);
			System.out.println("sql 실행 완료!");
			
			while(rset.next()) {
				String eventId = rset.getString("EVENT_ID");
				String title = rset.getString("TITLE");
				String desc = rset.getString("DESCRIPTION");
				String startDate = rset.getString("START_DATE");
				String endDate = rset.getString("END_DATE");
				int rewardPoint = rset.getInt("REWARD_POINT");
				System.out.println("이벤트 아이디 :" + eventId + "이벤트 제목 : " + title + "설명 : " + desc + "시작날짜 : " + startDate + "마지막날짜 : " + endDate);
			}
			
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rset != null && !rset.isClosed()) {
					rset.close();
				} 
			}catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(stmt != null && !stmt.isClosed()) {
					stmt.close();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
