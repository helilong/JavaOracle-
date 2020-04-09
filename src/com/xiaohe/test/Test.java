package com.xiaohe.test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Test {
public static void main(String[] args) throws ClassNotFoundException, SQLException {
	
	
			//¼ÓÔØÇý¶¯
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn =DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","he","123456");
			
			//sql
			String sql = "select * from userinfo1";
			
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ResultSet rs = ps.executeQuery();
			
			while(rs.next()) {
				System.out.println(rs.getInt("id")+"------"+rs.getString("uname"));
			}
			
			rs.close();
			ps.close();
			conn.close();
}
}
