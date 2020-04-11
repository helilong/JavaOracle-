package com.xiaohe.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.xiaohe.model.Userinfo;
import com.xiaohe.util.PageModel;


/*
 * 提供针对表的增删改查操作的接口
 */
public interface Dao {
	public ArrayList<Userinfo> select();
	
	
	public ArrayList<Userinfo> proSelect();
	public int proInsert(Userinfo u);
	public int proInsert2(Userinfo u);
	
	
	
	public PageModel<Userinfo> getAllUserinfo(int PageNo);
	
	public int getCount(Connection conn,PreparedStatement pstmt,ResultSet rs);
	
	
	public PageModel<Userinfo> getAllUserinfo1(int PageNo);
}
