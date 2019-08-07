<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ page isErrorPage="true" %>
<!DOCTYPE html>

<%!String errorMSG; %>
<% 
if(session.getAttribute("error msg")!=null)
	errorMSG = (String)session.getAttribute("error msg");
%>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<h1>Error Page</h1>
<br>
<%
if(errorMSG!=null){ 
%>
<h2><%=errorMSG %></h2>
<%
}
%>
</body>
</html>