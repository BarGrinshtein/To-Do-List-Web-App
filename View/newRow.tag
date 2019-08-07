
<%@ attribute name="taskName" required="true" rtexprvalue="true" %>
<%@ attribute name="taskID" required="true" rtexprvalue="true" %>
<%@ attribute name="userName" required="true" rtexprvalue="true" %>
<%@ attribute name="crateDate" required="true" rtexprvalue="true" %>
<%@ attribute name="expDate" required="true" rtexprvalue="true" %>



<% 
String taskN;
if(taskName == null)
	taskN = "";
else{
    taskN = taskName.toString();
}
String taskId = taskID.toString();
String userN = userName.toString();
String createD = crateDate.toString();
String expD;
if(expDate == null)
	expD = "";
else
	expD = expDate.toString();
%>

<tr><td><%=taskN %></td><td><%=taskId %></td><td><%=userN%></td><td><%=createD %></td><td><%=expD %></td><td><a href ="/Tosolist/router/task/deleteItem?id=<%=taskId %>" >Delete Task</a></td>
<td><a href= "/Tosolist/router/task/updateForm?id=<%=taskId%>">Update Task</a></td></tr>