<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<%!
  String msg = null;
%>

<%
if(request.getAttribute("msg") != null){
	msg = (String)request.getAttribute("msg");
}
%>


<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href="${pageContext.request.contextPath}/Styles/Login.css" rel="stylesheet" type= "text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
</head>

<body>

<h1 id = "title">To Do List!</h1>


<div id="mainDiv">
<h1>Login Form!</h1>

<form action="/Tosolist/router/login/login" method ="get" onsubmit = "return loginValidation();">
<br><input name= "userID" type = "number" id = "userID" min = "100000000" max = "999999999" placeholder="Input ID" />
<br>
<br><br><input name = "password" type = "password" id = "pass" placeholder="Input Password"/>
<br><br> <input value = "Login" type= "submit" class = "button"/>
</form>


<br>
<% 
if(msg!=null){
%>
<p id= "msg"><%=msg %></p>

<script>
document.getElementById("msg").style.color = "red";
</script>


<br>
<% } %>
<form action="/Tosolist/router/login/registrationForm" method = "get">
<input type= "submit" value= "Register" class = "button"/>
</form>
</div>
</body>
</html>


<% request.setAttribute("msg",null);%>