package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {
	
	// 0. import java.sql.*;
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";
	
	private void getConnection() {
		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);
			// System.out.println("접속성공");

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public void close() {
		// 5. 자원정리
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}
	
	public int userInsert(UserVo userVo) {
		
		int count = 0;
		
		getConnection();
	

		try {
		   
		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into users ";
			query += " values(seq_user_no.nextval, ?, ?, ?, ?) ";
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPw());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
			count = pstmt.executeUpdate();      
		    
		    // 4.결과처리
			System.out.println(count + "건 저장되었습니다.");

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		
		close();
		return count;
	}
	
	
	
	//유저 1명 정보 가져오기
	public UserVo getUser(String id, String pass) {
		
		UserVo userVo = null;
		
		getConnection();

		try {
		    
		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " select no, name ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";
			
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, id);
			pstmt.setString(2, pass);
			
			rs = pstmt.executeQuery();
		    
		    // 4.결과처리
			while(rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				
				//생성자가 없는 경우 setter 이용
	            userVo = new UserVo();
	            userVo.setNo(no);
	            userVo.setName(name);

			}

		
		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		close();
		return userVo;

	}
	
	//유저 1명 정보 가져오기
		public UserVo getUser(int uNo) {
			
			UserVo userVo = null;
			
			getConnection();

			try {
			    
			    // 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " select no, name, id, password, gender ";
				query += " from users ";
				query += " where no = ? ";
				
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, uNo);
				
				rs = pstmt.executeQuery();
			    
			    // 4.결과처리
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String id = rs.getString("id");
					String password = rs.getString("password");
					String gender = rs.getString("gender");
					
					userVo = new UserVo(no, id, password, name, gender);

					
				}

			
			} catch (SQLException e) {
			    System.out.println("error:" + e);
			} 
			close();
			return userVo;

		}
	
	
	//유저 1명 정보 수정하기
	public int setUser(UserVo userVo) {
		
		int count = 0;
		
		getConnection();
		

		try {
		    
		    // 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " update users ";
			query += " set password = ?, ";
			query += "     name = ?, ";
			query += "     gender = ? ";
			query += " where no = ? ";
			
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getPw());
			pstmt.setString(2, userVo.getName());
			pstmt.setString(3, userVo.getGender());
			pstmt.setInt(4, userVo.getNo());
			
			count = pstmt.executeUpdate();
		    
		    // 4.결과처리
			System.out.println(count +" 건 수정되었습니다.");

		} catch (SQLException e) {
		    System.out.println("error:" + e);
		} 
		return count;
	}
	
}






















