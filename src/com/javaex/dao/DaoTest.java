package com.javaex.dao;

import com.javaex.vo.UserVo;

public class DaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

//		UserVo userVo = new UserVo("aaa", "2345", "이효리", "female");
//	      
//	    UserDao userDao = new UserDao();
//	    userDao.userInsert(userVo);
		
		UserDao userDao = new UserDao();
		UserVo userVo = userDao.getUser("cjsrn1940", "1234");
		System.out.println(userVo);
		
	}

}
