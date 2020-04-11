package com.xiaohe.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.xiaohe.model.Userinfo;
import com.xiaohe.util.OracleUtil;
import com.xiaohe.util.PageModel;

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
			System.out.println("----------------修改"+count);
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

	
	public int proInsert2(Userinfo u) {
		// TODO Auto-generated method stub
		Connection conn =OracleUtil.getConn();
		CallableStatement cs = null;
		ResultSet rs = null;
		//构造sql语句
		String sql = "{call pro_insert2(?,?,?)}";
		
		try {
			cs = conn.prepareCall(sql);
			cs.setInt(1,u.getId());
			cs.setString(2,u.getUname());
			
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.VARCHAR);
			cs.execute();
			int out = cs.getInt(3);
			System.out.println("---------------------------------------"+out);
			//返回
			return out;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.getClose(conn, cs, rs);
		}
		return 0;
	}

	
	//分页查询
	public PageModel<Userinfo> getAllUserinfo(int PageNo){
		Connection conn=null;
		PreparedStatement pstmt=null;
		ResultSet rs= null;
		PageModel<Userinfo> pm=null;
		ArrayList<Userinfo> list= new ArrayList<Userinfo>();
		conn=OracleUtil.getConn();
		try {
			
			pstmt=conn.prepareStatement("select id,uname from (select rownum rn,u.* from userinfo1 u where rownum<=?* ?) n where n.rn>(?-1)*?");
			
			pstmt.setInt(1, PageNo);
			pstmt.setInt(2, 5);
			pstmt.setInt(3, PageNo);
			pstmt.setInt(4, 5);
			rs=pstmt.executeQuery();
			
			while(rs.next()){
				Userinfo info=new Userinfo(rs.getInt(1),rs.getString(2));
			
				list.add(info);
			}
			
			pm=new PageModel<Userinfo>(5);//带一个参数的构造方法
			pm.setList(list);//list集合里面的数据 给工具类的属性
			pm.setCurrentPage(PageNo);//设置当前页
			pm.setSumCount(getCount(conn, pstmt, rs));//调用获取总记录数的方法得到总记录数
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.getClose(conn, pstmt, rs);
		}
		return pm;
		
	}
	
	//获取总记录数
	@Override
	public int getCount(Connection conn, PreparedStatement pstmt, ResultSet rs) {
		String sql="select count(*)  from userinfo1";
		int count=0;
		try {
			pstmt=conn.prepareCall(sql);
			rs=pstmt.executeQuery();
			if(rs.next()){
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}


	/**
	 * 使用存储过程 分页
	 */

	@Override
	public PageModel<Userinfo> getAllUserinfo1(int PageNo) {
		Connection conn=null;
		CallableStatement cs=null;
		ResultSet rs= null;
		PageModel<Userinfo> pm=null;
		ArrayList<Userinfo> list= new ArrayList<Userinfo>();
		conn=OracleUtil.getConn();
		try {
			String sql="call pro_fenye1(?,?,?,?,?)";
			cs=conn.prepareCall(sql);
			
			cs.setString(1, "userinfo1");
			cs.setInt(2, PageNo);
			cs.setInt(3, 5);
			//注册输出参数类型
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			cs.registerOutParameter(5,oracle.jdbc.OracleTypes.CURSOR);
			//执行
			cs.execute();
			int sumcount=cs.getInt(4);
			rs=(ResultSet) cs.getObject(5);
			Userinfo info=null;
			while(rs.next()){
				info=new Userinfo(rs.getInt(1),rs.getString(2));
				System.out.println(rs.getInt("id"));
				System.out.println(rs.getString("name"));
				list.add(info);
			}
			
			pm=new PageModel<Userinfo>(5);//带一个参数的构造方法
			pm.setList(list);//list集合里面的数据 给工具类的属性
			pm.setCurrentPage(PageNo);//设置当前页
			pm.setSumCount(sumcount);//调用获取总记录数的方法得到总记录数
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.getClose1(conn, cs, rs);
		}
		return pm;
		
	}
	}	



		

