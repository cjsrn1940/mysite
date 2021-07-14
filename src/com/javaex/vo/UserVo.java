package com.javaex.vo;

public class UserVo {
	
	//필드
	private String id;
	private String pw;
	private String name;
	private String gender;
	
	
	
	//생성자
	public UserVo() {
	}
	
	public UserVo(String id, String pw, String name, String gender) {
		super();
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.gender = gender;
	}
	
	//gs
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}

	
	//일반
	@Override
	public String toString() {
		return "UserVo [id=" + id + ", pw=" + pw + ", name=" + name + ", gender=" + gender + "]";
	}
	
}
