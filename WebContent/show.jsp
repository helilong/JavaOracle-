<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.xiaohe.model.Userinfo"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<table border="1" width="60%" cellpadding="0" aligin="center">
	<caption>学生信息</caption>
	<tr>
		<th>编号</th>
		<th>姓名</th>
		<th>操作</th>
	</tr>

<%
	ArrayList<Userinfo> list = (ArrayList<Userinfo>)request.getAttribute("list");
	if(list != null && list.size()>0){
		for(Userinfo user:list){
			
			System.out.print(user.getId());
%>
<tr>
	<td><%= user.getId() %></td>
	<td><%= user.getUname()%></td>
	<td>
		<a href="#">修改</a>
		<a href="#">删除</a>
	</td>
</tr>
<%
		}
}
%>
	<tr >
		<td><a href="UserServlet?op=toadd">添加学生信息</a></td>
		
	</tr>

</table>


</body>
</html>