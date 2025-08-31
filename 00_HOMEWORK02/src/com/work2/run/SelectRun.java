package com.work2.run;

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
							   PLANT_ID
							 , PLANT_NAME
							 , PLANT_TYPE
					      FROM 
					           TB_PLANT
					     ORDER 
					        BY 
					           PLANT_ID
					           """;
		
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection("jdbc:oracle:thin:@115.90.212.20:10000:XE", "PSH08", "PSH081234");
			System.out.println("DB서버 접속 성공!");
			
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sql);
			
			while(rset.next()) {
				String name = rset.getString("PLANT_NAME");
				String type = rset.getString("PLANT_TYPE");
				System.out.println("식물이름 : " + name + "식물유형 : " + type);
			}
		} catch (ClassNotFoundException e) {
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
