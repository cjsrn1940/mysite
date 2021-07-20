package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

public class BoardDao {
	
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
		
		public List<BoardVo> boardList() {
			return boardList("");
		}
		
		//리스트
		public List<BoardVo> boardList(String keyword) {
			List<BoardVo> boardList = new ArrayList<BoardVo>();
			
			getConnection();
			
			try {
				
				
				// 3. SQL문 준비 / 바인딩 / 실행 --> 완성된 sql문을 가져와서 작성할것
				String query = "";
				query += " select b.no, b.title, u.name, b.hit, b.user_no, ";
				query += " b.reg_date ";
				query += " from board b, users u ";
				query += " where b.user_no = u.no ";
				

				if (keyword == null || keyword.equals("")) {//키워드가 없을 때  -- > null:
					query += " order by b.reg_date desc ";
					pstmt = conn.prepareStatement(query); // 쿼리로 만들기
				} else {
					query += " and(b.title || u.name || b.content) like ? ";
					query += " order by b.reg_date desc ";
					pstmt = conn.prepareStatement(query); // 쿼리로 만들기

					pstmt.setString(1, '%' + keyword + '%'); 
				}

				rs = pstmt.executeQuery();

				// 4.결과처리
				while (rs.next()) {
					int no = rs.getInt("no");
					String title = rs.getString("title");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					int user_no = rs.getInt("user_no");

					BoardVo boardVo = new BoardVo(no, title, hit, reg_date, name, user_no);
					boardList.add(boardVo);
				}

			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();

			
			return boardList;
			
		}
		
		//read
		public BoardVo getBoard(int no) {
			
			BoardVo boardVo = null;
			getConnection();

			try {
			    
			    // 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " select b.no, u.name, b.hit, b.reg_date, b.title, b.content, b.user_no ";
				query += " from board b, users u ";
				query += " where b.user_no = u.no ";
				query += " and b.no = ? ";
				
				
				pstmt = conn.prepareStatement(query);
				pstmt.setInt(1, no);
				
				rs = pstmt.executeQuery();
			    
			    // 4.결과처리
				while(rs.next()) {
					int num = rs.getInt("no");
					String name = rs.getString("name");
					int hit = rs.getInt("hit");
					String reg_date = rs.getString("reg_date");
					String title = rs.getString("title");
					String content = rs.getString("content");
					int user_no = rs.getInt("user_no");
					
					boardVo = new BoardVo(num, title, content, hit, reg_date,user_no, name);
		            
				}

			
			} catch (SQLException e) {
			    System.out.println("error:" + e);
			} 
			close();
			return boardVo;

		}
		
		//등록
		public int boardAdd(BoardVo boardVo) {
			int count = 0;
			getConnection();
			
			try {
				
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " insert into board(no, title, content, reg_date, user_no) ";
				query += " values(seq_board_no.nextval, ?, ?, sysdate, ?) ";
				//여기가
				
				System.out.println(query);
				
				pstmt = conn.prepareStatement(query);
				
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setInt(3, boardVo.getUser_no());
				
				count = pstmt.executeUpdate();
				
				// 4.결과처리
			} catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
			close();
			return count;
		}
		
		//삭제
		public int boardDelete(int no) {
			int count = 0;
			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " delete from board ";
				query += " where no = ? ";
				
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setInt(1, no);
				
				count = pstmt.executeUpdate(); // 쿼리문 실행


			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();
			return count;
		}

		//hit+1
		public int boardHitup(int no) {
			int count = 0;
			getConnection();

			try {
				// 3. SQL문 준비 / 바인딩 / 실행
				String query = ""; // 쿼리문 문자열만들기, ? 주의
				query += " update board set hit = hit+1 ";
				query += " where no = ? ";
				
				pstmt = conn.prepareStatement(query); // 쿼리로 만들기

				pstmt.setInt(1, no);
				
				count = pstmt.executeUpdate(); // 쿼리문 실행


			} catch (SQLException e) {
				System.out.println("error:" + e);
			}

			close();
			return count;
		}
		
		//수정
		public int boardUpdate(BoardVo boardVo) {
			int count = 0;
			
			getConnection();
			

			try {
			    
			    // 3. SQL문 준비 / 바인딩 / 실행
				String query = "";
				query += " update board ";
				query += " set title = ?, ";
				query += "     content = ? ";
				query += " where no = ? ";
				
				pstmt = conn.prepareStatement(query);
				pstmt.setString(1, boardVo.getTitle());
				pstmt.setString(2, boardVo.getContent());
				pstmt.setInt(3, boardVo.getNo());
				
				count = pstmt.executeUpdate();
			    
			    // 4.결과처리
				System.out.println(count +" 건 수정되었습니다.");

			} catch (SQLException e) {
			    System.out.println("error:" + e);
			} 
			return count;
		}
}
