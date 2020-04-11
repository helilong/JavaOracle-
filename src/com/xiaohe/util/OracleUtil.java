package com.xiaohe.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * 数据库工具类
 */
public class OracleUtil {
	private static final String Driver = "oracle.jdbc.driver.OracleDriver";
	private static final String Connect = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String User="he";
	private static final String Pwd="123456";
	
	
	static {
		try {
			Class.forName(Driver);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//获取连接
	public static Connection getConn() {
	
		try {
			Connection conn = DriverManager.getConnection(Connect,User,Pwd);
			return conn;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	//关闭连接
	public static void getClose(Connection conn,PreparedStatement ps,ResultSet rs) {
		
			try {
				if(conn != null) {
				conn.close();
				}
				
				if(ps != null) {
					ps.close();
				}
				
				if(rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	//静态方法 关闭连接
		public static void getClose1(Connection conn,CallableStatement cs,ResultSet rs) {
			
				try {
					
					if(rs!=null) rs.close();
					if(cs!=null) cs.close(); //关闭存储过程
					if(conn!=null) conn.close();
				
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	
}
