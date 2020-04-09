package com.xiaohe.model;
/**
 * 学生的实体类：封装表字段
 * @author xiaohe
 *
 */
public class Userinfo {
	private int id;
	private String uname;
	
	
	
	
	
	public Userinfo(int id, String uname) {
		super();
		this.id = id;
		this.uname = uname;
	}
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	
	
	
	
}
