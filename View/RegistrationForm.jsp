<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%!String msg; %>
<% 
if(request.getAttribute("msg")!= null)
	msg = (String)request.getAttribute("msg");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href ="${pageContext.request.contextPath}/Styles/Registration.css" rel = "stylesheet" type="text/css" >
<script type="text/javascript" src ="${pageContext.request.contextPath}/js/validation.js"></script>
</head>
<body>

<h1 id = "title">To Do List!</h1>

<div>
<h1>Registration Form!</h1>
<form action="/Tosolist/router/login/register" method="get" onsubmit="return registerationValidation();"> 
         <input type="text" name = "userName" id = "userName" placeholder="Set User Name"/>
<br><br><input type ="number" name = "userID" min="100000000" max="999999999" id = "userID" placeholder="Input User ID"/>
<br><br><input type = "text" name = "password" id = "pass" placeholder="Input Password"/>
<br><br><input type = "submit" value = "Finish"/>
</form>
<%
if(msg != null){
%>
<p><%=msg%></p>
<%
}
%>
</div>
</body>
</html>

<% request.setAttribute("msg",null);%>