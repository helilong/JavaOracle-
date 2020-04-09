package com.xiaohe.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaohe.dao.DaoImpl;
import com.xiaohe.model.Userinfo;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("utf-8");
		
		
		DaoImpl userDao = new DaoImpl();
		
		//获取op
		String op = request.getParameter("op");
		System.out.println(op);
		if(op == null || "".equals(op)) {
			//调用daoImpl 查询
			ArrayList<Userinfo> list = userDao.select();
			request.setAttribute("list", list);
			
			
			
			
			//存储
			ArrayList<Userinfo> list1 =userDao.proSelect();
			request.setAttribute("list1", list1);
			
			request.getRequestDispatcher("show.jsp").forward(request, response);
		}else if("toadd".equals(op)){
			response.sendRedirect("add.jsp");
		}else if("add".equals(op)) {
			//取值
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("uname");
			
			Userinfo u = new Userinfo(id,name);
			int count = userDao.proInsert(u);
			System.out.println("------"+count);
			if(count==0) {
				response.sendRedirect("UserServlet");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
