<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="UserServlet">
	<input type="hidden" name="op" value="add"/>
	id<input name="id"/>
	<br>
	用户名：<input name="uname"/>	
	<br>
	<input type="submit" value="提交"/>
</form>
</body>
</html>