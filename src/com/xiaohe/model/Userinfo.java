package com.xiaohe.model;
/**
 * ѧ����ʵ���ࣺ��װ���ֶ�
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
