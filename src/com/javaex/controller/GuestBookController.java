package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/guest")
public class GuestBookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("게스트북 컨트롤러");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("addList".equals(action)) {
			System.out.println("[리스트]");
			
			GuestbookDao guestDao = new GuestbookDao();
			List<GuestbookVo> guestList = guestDao.getGuestList();
			
			request.setAttribute("gList", guestList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		
		} else if ("add".equals(action)) {
			System.out.println("[등록]");
			
			GuestbookDao guestDao = new GuestbookDao();
			
			String name = request.getParameter("name");
			String password = request.getParameter("pass");
			String content = request.getParameter("content");
			
			GuestbookVo guestVo = new GuestbookVo(name, password, content);
			
			guestDao.guestInsert(guestVo);
			
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");
			
		} else if("dform".equals(action)) {
			System.out.println("[삭제폼]");
			
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		
		} else if("delete".equals(action)) {
			System.out.println("[삭제]");

			GuestbookDao guestDao = new GuestbookDao();

			int no = Integer.parseInt(request.getParameter("no"));
			
			String password = request.getParameter("pass");
			System.out.println(password);
			
			int count = guestDao.guestDelete(no, password);
			
			WebUtil.redirect(request, response, "/mysite/guest?action=addList");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
















