package com.xiaohe.dao;

import java.util.ArrayList;

import com.xiaohe.model.Userinfo;

/*
 * �ṩ��Ա����ɾ�Ĳ�����Ľӿ�
 */
public interface Dao {
	public ArrayList<Userinfo> select();
	
	
	public ArrayList<Userinfo> proSelect();
	public int proInsert(Userinfo u);
}
