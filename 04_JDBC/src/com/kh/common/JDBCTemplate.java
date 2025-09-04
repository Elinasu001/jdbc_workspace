package com.kh.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCTemplate {
	/*
	 * JDBC과정 중 반복적으로 쓰이는 구문들을 각각의 메소드로 정의해둘 클래스
	 * 중복된 코드들을 메소드로 분리하여 '재사용'
	 * 
	 * 이 클래스의 모든 메소드는 전부 static으로 선언
	 */
	
	// 객체 만들지 않을 경우 ? static
	
	
	/**
	 * JDBC Driver를 등록하는 메소드
	 * 프로그램 실행 중 "단 한 번만" 실행
	 */
	public static void regisDriver() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return
	 * DB 연결정보를 가지고 있는 Connection객체를 생성해서 반환해주는 메소드
	 */
	public static Connection getConnection() {
		
		Properties prop = new Properties();
		
		try {
			
			prop.load(new FileInputStream("resources/driver.properties"));
			
			//String keyA = prop.getProperty("A");
			
			//System.out.println("A 키값의 Value :" + keyA);
			//System.out.println(prop.getProperty("URL"));
			
			Connection conn = DriverManager.getConnection(prop.getProperty("URL"), prop.getProperty("USERNAME"), prop.getProperty("PASSWORD"));
			
			conn.setAutoCommit(false);
			
			return conn;
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/** 
	 * 트랜잭션 처리 메소드
	 * Connection 객체를 이용해서 commit 시켜주는 메소드
	 * @param conn
	 */
	public static void commit(Connection conn) {
		try {
			if(conn != null) {
				conn.commit();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Connection 객체를 이용해서 rollback 시켜주는 메소드
	 * @param conn
	 */
	public static void rollback(Connection conn) {
		try {
			if(conn != null) {
				conn.rollback();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * JDB용 객체를 반납해주는 메소드(각 객체별로)
	 * Connection 객체를 전달받아서 반납해주는 메소드
	 * @param conn
	 */
	public static void close(Connection conn) {
		try {
			if(conn != null) {
				conn.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Statement 객체를 전달받아서 반납해주는 메소드
	 * => 다형성을 적용하여 PreparedStatment 객체도 Statement 타입으로 받을 수 있음(부모자식 관계)
	 * @param stmt
	 */
	public static void close(Statement stmt) { // 매개변수 타입이 다르기 때문에 (메소드 오버로딩(Overloading) + 정적 바인딩) 메소드명 동일 가능 _ close불리지는 컴파일 시점(정적 바인딩) 호출부의 변수 타입으로 결정
		try {
			if(stmt != null) {
				stmt.close();
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * ResultSet 객체로 전달받아서 반납해주는 메소드
	 * @param rset
	 */
	public static void close(ResultSet rset) {
		try {
			if(rset != null) {
				rset.close();
			}
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	
}
