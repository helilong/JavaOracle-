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
 * �ṩ��Ա����ɾ�Ĳ������ʵ�ֽӿ�
 */
public class DaoImpl implements Dao{

	@Override
	public ArrayList<Userinfo> select() {
		// TODO Auto-generated method stub
		//��������
		Connection conn =OracleUtil.getConn();
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		ArrayList<Userinfo> list = new ArrayList<Userinfo>();
		
		//����sql���
		String sql = "select * from userinfo1";
		
		//������������
		try {
			ps = conn.prepareStatement(sql);
			
			//Ϊ�� ��ֵ
			rs = ps.executeQuery();
			
			//���н����
			while(rs.next()) {
				
				//�ӽ������ȡ������
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
		//��������
			Connection conn =OracleUtil.getConn();
			PreparedStatement ps = null;
			CallableStatement cs = null;
			ResultSet rs = null;
			ArrayList<Userinfo> list = new ArrayList<Userinfo>();
			
			//����sql���
			String sql = "{call pro_select(?)}";
			//����Ԥ�������
			try {
				cs = conn.prepareCall(sql);
				
				//ע�������������
				cs.registerOutParameter(1, oracle.jdbc.OracleTypes.CURSOR);
				
				//ִ��
				cs.execute();
				
				//��ȡ���ݲ�����ֵ
				rs = (ResultSet) cs.getObject(1);
				
				//���н����
				while(rs.next()) {
					
					//�ӽ������ȡ������
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
		
		

		//����sql���
		String sql = "{call pro_insert(?,?)}";
		
		try {
			cs = conn.prepareCall(sql);
			cs.setInt(1,u.getId());
			cs.setString(2,u.getUname());
			//����
			
			int count = cs.executeUpdate();
			System.out.println("----------------�޸�"+count);
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
		//����sql���
		String sql = "{call pro_insert2(?,?,?)}";
		
		try {
			cs = conn.prepareCall(sql);
			cs.setInt(1,u.getId());
			cs.setString(2,u.getUname());
			
			cs.registerOutParameter(3, oracle.jdbc.OracleTypes.VARCHAR);
			cs.execute();
			int out = cs.getInt(3);
			System.out.println("---------------------------------------"+out);
			//����
			return out;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.getClose(conn, cs, rs);
		}
		return 0;
	}

	
	//��ҳ��ѯ
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
			
			pm=new PageModel<Userinfo>(5);//��һ�������Ĺ��췽��
			pm.setList(list);//list������������� �������������
			pm.setCurrentPage(PageNo);//���õ�ǰҳ
			pm.setSumCount(getCount(conn, pstmt, rs));//���û�ȡ�ܼ�¼���ķ����õ��ܼ�¼��
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.getClose(conn, pstmt, rs);
		}
		return pm;
		
	}
	
	//��ȡ�ܼ�¼��
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
	 * ʹ�ô洢���� ��ҳ
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
			//ע�������������
			cs.registerOutParameter(4, oracle.jdbc.OracleTypes.NUMBER);
			cs.registerOutParameter(5,oracle.jdbc.OracleTypes.CURSOR);
			//ִ��
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
			
			pm=new PageModel<Userinfo>(5);//��һ�������Ĺ��췽��
			pm.setList(list);//list������������� �������������
			pm.setCurrentPage(PageNo);//���õ�ǰҳ
			pm.setSumCount(sumcount);//���û�ȡ�ܼ�¼���ķ����õ��ܼ�¼��
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			OracleUtil.getClose1(conn, cs, rs);
		}
		return pm;
		
	}
	}	



		

