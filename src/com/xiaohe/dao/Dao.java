package com.xiaohe.dao;

import java.util.ArrayList;

import com.xiaohe.model.Userinfo;

/*
 * 提供针对表的增删改查操作的接口
 */
public interface Dao {
	public ArrayList<Userinfo> select();
	
	
	public ArrayList<Userinfo> proSelect();
	public int proInsert(Userinfo u);
}
