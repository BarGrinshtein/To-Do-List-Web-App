package hit.bar.todolist.controllers;

import java.io.IOException;
import java.util.Iterator;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import hit.bar.todolist.model.*;

public class TaskController {
	
	HibernateToDoListDAO hibernateToDoListDAO;
	
	public TaskController() {
		hibernateToDoListDAO = HibernateToDoListDAO.getInstance();
	}
	
	public void addItem(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("itemName") == null || request.getParameter("ExpD") == null)
			request.getRequestDispatcher("/ListScreen.jsp").forward(request, response);
		else {
		//get user from the session
		HttpSession session = request.getSession();
		
		User user = (User)session.getAttribute("user");
		
		//set a new item object
		Item item = new Item();
		item.setName(request.getParameter("itemName"));
		item.setCreationDate((String)session.getAttribute("date"));
		item.setExpDate(request.getParameter("ExpD"));
		item.setUserID(user.getId());
		item.setUser(user);
		user.getItems().add(item);
		
		//update the database
		hibernateToDoListDAO.addItem(item);
		
		//Redirect to ListScreen.jsp
		session.setAttribute("items", hibernateToDoListDAO.getUserItems(user.getId()));
		RequestDispatcher ds = request.getRequestDispatcher("/ListScreen.jsp");
		ds.forward(request, response);
		}
	}
	
	public void deleteItem(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		
		//get user and item from the session
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user");
	    Item item = hibernateToDoListDAO.getItem(Integer.parseInt(request.getParameter("id")));
	    
	    if(item == null)
	    	request.getRequestDispatcher("/ListScreen.jsp").forward(request, response);
	    
		user.getItems().remove(item);
		
		//delete the item from the database
		hibernateToDoListDAO.deleteItem(item.getCode());
		
		//Redirect to ListScreen.jsp
		session.setAttribute("items", hibernateToDoListDAO.getUserItems(user.getId()));
		RequestDispatcher ds = request.getRequestDispatcher("/ListScreen.jsp");
		ds.forward(request, response);
	}
	
	public void updateForm(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		Item item = hibernateToDoListDAO.getItem(Integer.parseInt(request.getParameter("id")));
		request.setAttribute("taskName", item.getName());
		request.setAttribute("id", String.valueOf(item.getCode()));
		request.getRequestDispatcher("/UpdateForm.jsp").forward(request, response);
	}
	
	
	public void updateItem(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException,TodoListExaption {
	    		
		//get item to be updated
		HttpSession session = request.getSession();
		if(request.getParameter("id") == null || request.getParameter("taskName") == null)
			request.getRequestDispatcher("/ListScreen.jsp").forward(request, response);
		int id = (Integer.parseInt(request.getParameter("id")));
		String expDate = (String)request.getParameter("date");
		String taskName = (String)request.getParameter("taskName");
	    User user = (User)session.getAttribute("user");
	    Iterator<Item> iterator;
	    try {
	        iterator = user.getItems().iterator();
	    }
	    catch (Exception e) {
			throw new TodoListExaption("Connection error has accured, please refresh your browser.");
		}
	    Item curr = null;
	    while(iterator.hasNext()) {
	    	 curr = (Item)iterator.next();
	    	if(curr.getCode() == id) {
	    		curr.setName(taskName);
	    		curr.setExpDate(expDate);
	    		break;
	    	}
	    }
	    
	    //update the database
	    hibernateToDoListDAO.updateItem(curr);
	    
	    //Redirect to ListScreen.jsp
	    session.setAttribute("items", hibernateToDoListDAO.getUserItems(user.getId()));
		RequestDispatcher ds = request.getRequestDispatcher("/ListScreen.jsp");
		ds.forward(request, response);
	    
	}

}
