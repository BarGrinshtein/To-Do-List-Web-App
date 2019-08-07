
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import ="hit.bar.todolist.model.User" %>
<%@ page import ="hit.bar.todolist.model.Item" %>
<%@ page import = "java.util.List" %>

<!DOCTYPE html>
<%!
List<Item> items;

String todaysDate;

String taskName;
int taskID;
String userName;
String craeteDate;
String expDate;
%>

<jsp:useBean id="user" scope="session" type="hit.bar.todolist.model.User"></jsp:useBean>

<%

   if(session.getAttribute("items")!=null){
        items = (List<Item>)session.getAttribute("items");
   }
   
   if(session.getAttribute("date")!= null)
	   todaysDate = (String)session.getAttribute("date");
%>


<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>

<link href = "${pageContext.request.contextPath}/Styles/List.css" rel = "stylesheet" type="text/css">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/validation.js"></script>
</head>


<body onload ="setMinDate();">
<%@ taglib tagdir="/WEB-INF/tags" prefix="print"%>

<div id = "titleDiv">
<h1><jsp:getProperty property="name" name="user"/>, Welcome!</h1>
<h1 id = "title">The List</h1>
</div>

<div id = "listDiv">
<table>
<tr bgcolor="GREEN"><th>Task Name</th><th>Task ID</th><th>User Name</th><th>Creation Date</th><th>Expiration Date</th><th colspan="2">Actions</th></tr> 
<%
if(items != null)
{
  for(Item item : items){
	  taskName = item.getName();
	  taskID = item.getCode();
	  userName = item.getUser().getName();
	  craeteDate = item.getCreationDate();
	  expDate = item.getExpDate();
%>
<print:newRow taskID="<%=String.valueOf(taskID) %>" userName="<%= userName %>" taskName="<%=taskName %>" crateDate="<%=craeteDate %>" expDate="<%=expDate %>"></print:newRow>
	  
<% 
  }
}
%>  
</table>
</div>
<br><br>

<!-- Add new task to the todolist -->
<div id = "addTaskDiv">
<form action = "/Tosolist/router/task/addItem" method = "post"  onsubmit="return newTaskValidation();">
<fieldset>
<legend>Add new task:</legend>
<br>
<label for="itemName">Task Name:</label><input id = "taskName" type="text" name = "itemName"/>  <input type= "submit" value = "Add"/>
<br>
<label for = "ExpD">Expired Date:</label><input id = "expDate" type = "date" name = "ExpD" />

<script type="text/javascript">
document.getElementById("expDate").valueAsDate = new Date();
</script>

<br><p id = "failMsg"></p>
</fieldset>
</form>

<br>


<!-- Logout form -->
<form action="/Tosolist/router/login/logout" method = "post">
<input type = "submit" value = "logout"/>
</form>
</div>

</body>
</html>