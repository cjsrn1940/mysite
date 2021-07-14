package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

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
	
	//추가
	public int guestInsert(GuestbookVo guestVo) {
		int count = 0;
		getConnection();
		
		try {
			
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = "";
			query += " insert into guestbook ";
			query += " values(seq_guestbook_id.nextval, ?, ?, ?, sysdate) ";
			//여기가
			
			System.out.println(query);
			
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, guestVo.getName());
			pstmt.setString(2, guestVo.getPassword());
			pstmt.setString(3, guestVo.getContent());
			
			count = pstmt.executeUpdate();
			
			// 4.결과처리
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return count;
		
	}
	
	
	//리스트(검색안할때)
	public List<GuestbookVo> getGuestList() {
		return getGuestList("");
	}
	
	//리스트(검색할때)
	public List<GuestbookVo> getGuestList(String keyword) {
		List<GuestbookVo> guestList = new ArrayList<GuestbookVo>();
		
		getConnection();
		
		try {

			// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
			String query = "";
			query += " select  no, ";
			query += "         name, ";
			query += "         password, ";
			query += "         content, ";
			query += " 		   reg_date ";
			query += " from guestbook ";

			if (keyword != "" || keyword == null) {
				query += " where name like ? ";
				query += " or password like  ? ";
				query += " or content like ? ";
				query += " or reg_date like ? ";
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setString(1, '%' + keyword + '%'); // ?(물음표) 중 1번째, 순서중요
				pstmt.setString(2, '%' + keyword + '%'); // ?(물음표) 중 2번째, 순서중요
				pstmt.setString(3, '%' + keyword + '%'); // ?(물음표) 중 3번째, 순서중요
				pstmt.setString(4, '%' + keyword + '%'); // ?(물음표) 중 4번째, 순서중요
			} else {
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기
			}

			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");
				String password = rs.getString("password");
				String content = rs.getString("content");
				String reg_date = rs.getString("reg_date");

				GuestbookVo guestVo = new GuestbookVo(no, name, password, content, reg_date);
				guestList.add(guestVo);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return guestList;

	}
	
	
	
	//삭제
	public int guestDelete(int no, String password) {
		int count = 0;
		getConnection();

		try {
			// 3. SQL문 준비 / 바인딩 / 실행
			String query = ""; // 쿼리문 문자열만들기, ? 주의
			query += " delete from guestbook ";
			query += " where no = ? ";
			query += " and password = ? ";
			
			pstmt = conn.prepareStatement(query); // 쿼리로 만들기

			pstmt.setInt(1, no);
			pstmt.setString(2, password);
			
			count = pstmt.executeUpdate(); // 쿼리문 실행

			// 4.결과처리
			// System.out.println(count + "건 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}
	
}

















