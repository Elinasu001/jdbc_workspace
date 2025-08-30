package com.work2.run;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class InsertRun {

	public static void main(String[] args) {
		
		Connection conn = null;
		Statement stmt = null;
		int result = 0;
		
		
		Scanner sc = new Scanner(System.in);
		System.out.println("식물 이름을 입력해주세요. >");
		String name = sc.nextLine();
		
		System.out.println("식물 유형을 입력해주세요. > ");
		String type = sc.nextLine();
		
		String sql = "INSERT INTO TB_PLANT (PLANT_ID, PLANT_NAME, PLANT_TYPE)"
				   +  "VALUES (SEQ_PLANT.NEXTVAL, "
				   +  "'" + name + "', "
				   +  "'" + type + "')";
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("Driver 성공");
			
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			System.out.println("DB서버 접속 성공!");
			conn.setAutoCommit(false);
			
			stmt = conn.createStatement();
			System.out.println("Statement 객체 생성!");
			
			result = stmt.executeUpdate(sql);		
			System.out.println("SQL문 실행 성공!");
			
			if(result > 0) {
				conn.commit();
			}
			
		}catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (stmt != null) stmt.close();
				if (conn != null) conn.close();
				sc.close();
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
