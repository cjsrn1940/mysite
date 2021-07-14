package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//텍스트 인코딩
		request.setCharacterEncoding("UTF-8");
		
		System.out.println("[UserController]");
		
		String action = request.getParameter("action");
		
		if("joinForm".equals(action)) { //회원가입 폼
			System.out.println("[UserController.joinForm]");
			
			//회원가임폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		
		} else if("join".equals(action)) { //회원가입
			System.out.println("[UserController.join]");
			
			//**********회원가입************
			
			//파라미터 꺼내기
			String id = request.getParameter("id");
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			//System.out.println(id + ", " + pw + ", " + name + ", " + gender);
			
			//vo만들기
			UserVo userVo = new UserVo(id, pw, name, gender);
			System.out.println(userVo);
			
			//dao.Insert(vo) --> db저장
			UserDao userDao = new UserDao();
			int count = userDao.userInsert(userVo);
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
		} else if("loginForm".equals(action)) {
			System.out.println("[UserController.loginForm]");
			
			//포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		}
		
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
