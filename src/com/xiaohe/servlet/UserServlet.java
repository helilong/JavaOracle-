package com.xiaohe.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.xiaohe.dao.DaoImpl;
import com.xiaohe.model.Userinfo;
import com.xiaohe.util.PageModel;

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
			int count1 = userDao.proInsert(u);
		//int count = userDao.proInsert2(u);
			System.out.println("-----111111111111--------"+count1);
			if(count1>0) {
				response.sendRedirect("UserServlet");
			}
		}else if("fenye".equals(op)) {
			
			String pageNo=null;
			pageNo=request.getParameter("pageNo");
			if(pageNo==null){
				pageNo="1";
				
			}
			PageModel<Userinfo> pm=userDao.getAllUserinfo1(Integer.parseInt(pageNo));
			request.setAttribute("allinfo",pm);
			request.setAttribute("list",pm.getList());
			request.getRequestDispatcher("show_fenye.jsp").forward(request,response);
			
			
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
