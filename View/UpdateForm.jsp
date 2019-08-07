<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>

<%!
String taskName = null;
int id;
%>

<% 
taskName = (String)request.getAttribute("taskName");
id = Integer.parseInt((String)request.getAttribute("id"));
 %>

<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link href = "${pageContext.request.contextPath}/Styles/Update.css" rel = "stylesheet" type= "text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
</head>
<body onload ="setMinDate();">

<h1>UPDATE TASK'S DETAILS</h1>
<div>
<form action="/Tosolist/router/task/updateItem" method = "post" onsubmit = "return newTaskValidation();">
<fieldset>
<legend >Update Task</legend>
<label for ="taskName">Task Name:</label><input id = "taskName" type = "text" name = "taskName" value = "<%=taskName %>"/>

<br>

<label for = "date">Expiration Date:</label><input id = "expDate" type="date" name = "date"/> 

<script type="text/javascript">
document.getElementById("expDate").valueAsDate = new Date();
</script>

<input type ="hidden" name = "id" value = "<%=String.valueOf(id)%>"/>

<br>

<input type = "submit" value = "update"/>
</fieldset>
</form>
</div>
</body>
</html>