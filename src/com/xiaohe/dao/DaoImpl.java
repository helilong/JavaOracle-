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
			System.out.println("�޸�"+cs.executeUpdate());
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
