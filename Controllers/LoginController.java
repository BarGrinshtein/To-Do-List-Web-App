package hit.bar.todolist.controllers;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import hit.bar.todolist.model.*;

public class LoginController {
	
	HibernateToDoListDAO hibernateDAO;
	
	public LoginController() {
		hibernateDAO = HibernateToDoListDAO.getInstance();
	}
	
	private String getTodaysDate() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int month = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DAY_OF_WEEK) + 1;
		return year + "-" + month + "-" + day ;
	}
	
	public void loginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//set todays date
		HttpSession session =request.getSession();
		session.setAttribute("date", getTodaysDate());
		
		//check for Cookies
		Cookie[] cookies = request.getCookies();
		RequestDispatcher ds;
		int userID = 0;
		String password = null;
		
		if(cookies != null) {
		if(cookies.length > 1) {
			for(Cookie c : cookies) {
				if(c.getName().equals("userID"))
					userID = Integer.parseInt(c.getValue());
				else if(c.getName().equals("password"))
					password = c.getValue();
			}
			if(userID!= 0 && password !=null) {
			   ds = request.getRequestDispatcher("/router/login/login?userID="+userID+"&password="+password);
			   ds.forward(request,response);
			}
		  }
		}
        ds = request.getRequestDispatcher("/LoginForm.jsp");
        ds.forward(request,response);
	}
	
	public void registrationForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher ds;
        ds = request.getRequestDispatcher("/RegistrationForm.jsp");
        ds.forward(request,response);
	}
	
	public void login(HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

        // get the user from the login jsp.
        User user = new User();
        user.setId(Integer.parseInt(request.getParameter("userID")));
        user.setPassword(request.getParameter("password"));
        HttpSession session = request.getSession();

        //check for id + password validation
        RequestDispatcher ds;
        boolean validation = true;
        try {
            validation = hibernateDAO.validationTest(user.getId(),user.getPassword());
        }
        catch (TodoListExaption e) {
        	session.setAttribute("error msg", e.getMessage());
		}
        if (validation == true){
            user = hibernateDAO.getUser(Integer.parseInt(request.getParameter("userID")));
            session.setAttribute("user", user);
            if(user.getItems() != null) 
            	session.setAttribute("items", hibernateDAO.getUserItems(user.getId()));
            
            //create Cookies for login members
            Cookie idCookie = new Cookie("userID",String.valueOf(user.getId()));
            idCookie.setMaxAge(60*60*24*2);
            idCookie.setPath("/");
            response.addCookie(idCookie);
            
            Cookie passwordCookie = new Cookie("password",user.getPassword());
            passwordCookie.setMaxAge(60*60*24*2);
            passwordCookie.setPath("/");
            response.addCookie(passwordCookie);
            
            ds = request.getRequestDispatcher("/ListScreen.jsp");
			ds.forward(request,response);
        }
        else {
            String msg = "Invalid ID or password, please try again.";
            request.setAttribute("msg",msg);
            ds = request.getRequestDispatcher("/LoginForm.jsp");
            ds.forward(request,response);
        }
	}
	
	public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        // get user from the RegistrationForm.jsp
        User user = new User();
        user.setName(request.getParameter("userName"));
        user.setId(Integer.parseInt(request.getParameter("userID")));
        user.setPassword(request.getParameter("password"));

        // add user to the database + check if id/password are already taken.
        try {
            hibernateDAO.addUser(user);
        }
        //in case the registration not succeeded
        catch (TodoListExaption ex){
            String msg = null;
            if(ex.getMessage().equals("ID"))
                msg = "This ID is already exist!";
            else if(ex.getMessage().equals("Password"))
                msg = "This password is already taken, please set another.";
            request.setAttribute("msg",msg);
            RequestDispatcher rd = request.getRequestDispatcher("/RegistrationForm.jsp");
            rd.forward(request,response);
        }
        
        //create Cookies for new members
        Cookie idCookie = new Cookie("userID",String.valueOf(user.getId()));
        idCookie.setMaxAge(60*60*24*2);
        idCookie.setPath("/");
        response.addCookie(idCookie);
        
        Cookie passwordCookie = new Cookie("password",user.getPassword());
        passwordCookie.setMaxAge(60*60*24*2);
        passwordCookie.setPath("/");
        response.addCookie(passwordCookie);
        
        // in case the registration succeeded
        HttpSession session = request.getSession();
        session.setAttribute("user", user);
        session.setAttribute("items",user.getItems());
        RequestDispatcher rd = request.getRequestDispatcher("/ListScreen.jsp");
        rd.forward(request,response);
	}
	
	public void logout(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
		Cookie cookie = new Cookie("userID","");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		cookie = new Cookie("password","");
		cookie.setMaxAge(0);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		HttpSession session = request.getSession();
		session.setAttribute("items", null);
		request.setAttribute("msg",null);		
		session.invalidate();
		RequestDispatcher ds = request.getRequestDispatcher("/LoginForm.jsp");
		ds.forward(request, response);
	}
}
