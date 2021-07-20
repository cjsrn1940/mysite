package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		System.out.println("보드컨트롤러");
		// post일때 한글 깨짐방지
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if("list".equals(action)) {
			System.out.println("[리스트]");
			
			BoardDao boardDao = new BoardDao();
		
			List<BoardVo> boardList;
			String keyword = request.getParameter("keyword");
			
			if(keyword != null) {
				boardList = boardDao.boardList(keyword);
			} else {
				boardList = boardDao.boardList();
			}
			
			request.setAttribute("bList", boardList);
			
			HttpSession session = request.getSession();
			
			int size = boardList.size();
			session.setAttribute("size", size);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		} else if("wForm".equals(action)) {
			System.out.println("[등록폼]");
			
			HttpSession session = request.getSession();
			
			System.out.println("1");
			if(session.getAttribute("authUser") != null) {
				System.out.println("3");
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			} else {
				System.out.println("2");
				WebUtil.redirect(request, response, "/mysite/board?action=list");
			}
			
			
		} else if("add".equals(action)) {
			System.out.println("[등록]");
			
			HttpSession session = request.getSession();
			
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardVo boardVo = new BoardVo(title, content, no);
			BoardDao boardDao = new BoardDao();
			
			boardDao.boardAdd(boardVo);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
		} else if("read".equals(action)) {
			System.out.println("[읽기]");
			
			BoardDao boardDao = new BoardDao();
			
			int no = Integer.parseInt(request.getParameter("no"));
			boardDao.boardHitup(no);
			
			BoardVo boardVo = (BoardVo)boardDao.getBoard(no);
			request.setAttribute("board", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
			
			
		} else if("delete".equals(action)) {
			System.out.println("[삭제]");
			
			BoardDao boardDao = new BoardDao();
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			boardDao.boardDelete(no);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
		
		} else if("mForm".equals(action)) {
			System.out.println("[수정폼]");
			
			BoardDao boardDao = new BoardDao();
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo boardVo = (BoardVo)boardDao.getBoard(no);
			request.setAttribute("board", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		} else if("modify".equals(action)) {
			System.out.println("[수정]");
			BoardDao boardDao = new BoardDao();
			
			int no = Integer.parseInt(request.getParameter("no"));
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardVo boardVo = new BoardVo(no, title, content);
			
			boardDao.boardUpdate(boardVo);
			
			WebUtil.redirect(request, response, "/mysite/board?action=list");
			
		}
		
	
	
	
	
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
