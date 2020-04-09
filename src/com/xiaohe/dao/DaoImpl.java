package com.xiaohe.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xiaohe.model.Userinfo;
import com.xiaohe.util.OracleUtil;

/*
 * 提供针对表的增删改查操作的实现接口
 */
public class DaoImpl implements Dao{

	@Override
	public ArrayList<Userinfo> select() {
		// TODO Auto-generated method stub
		//创建对象
		Connection conn =OracleUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Userinfo> list = new ArrayList<Userinfo>();
		
		//构成sql语句
		String sql = "select * from userinfo1";
		
		//创建域编译对象
		try {
			ps = conn.prepareStatement(sql);
			
			//为？ 赋值
			rs = ps.executeQuery();
			
			//编列结果集
			while(rs.next()) {
				
				//从结果集中取出数据
				Userinfo u = new Userinfo(rs.getInt("id"),rs.getString("uname"));
				System.out.println(rs.getInt("id"));
				list.add(u);	
			}
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.getClose(conn, ps, rs);
		}
		
		
		return list;
	}

	@Override
	public ArrayList<Userinfo> proSelect() {
		// TODO Auto-generated method stub
		//创建对象
			Connection conn =OracleUtil.getConn();
			PreparedStatement ps = null;
			CallableStatement cs = null;
			ResultSet rs = null;
			ArrayList<Userinfo> list = new ArrayList<Userinfo>();
			
			//构造sql语句
			String sql = "{call pro_select(?)}";
			//创建预编译对象
			try {
				cs = conn.prepareCall(sql);
				
				//注册输出参数类型
				cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
				
				//执行
				cs.execute();
				
				//获取数据参数的值
				rs = (ResultSet) cs.getObject(1);
				
				//编列结果集
				while(rs.next()) {
					
					//从结果集中取出数据
					Userinfo u = new Userinfo(rs.getInt("id"),rs.getString("uname"));
					System.out.println(rs.getInt("id"));
					list.add(u);	
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				OracleUtil.getClose(conn, ps, rs);
				
	
					try {
						if(cs != null) {
						cs.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
			}
			
		return null;
	}

	@Override
	public int proInsert(Userinfo u) {
		// TODO Auto-generated method stub
		Connection conn =OracleUtil.getConn();
		CallableStatement cs = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		

		//构造sql语句
		String sql = "{call pro_insert(?,?)}";
		
		try {
			cs = conn.prepareCall(sql);
			cs.setInt(1,u.getId());
			cs.setString(2,u.getUname());
			//返回
			
			int count = cs.executeUpdate();
			System.out.println("修改"+cs.executeUpdate());
			return count;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.getClose(conn, ps, rs);
			try {
				cs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0;
	}

	
}
