package hit.bar.todolist;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hit.bar.todolist.model.TodoListExaption;

/**
 * Servlet implementation class Router
 */
@WebServlet("/router/*")
public class Router extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	private final String pkg = "hit.bar.todolist.controllers";

	public Router() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String str = request.getPathInfo();
		String var[] = str.split("/");
		String controller = var[1].substring(0, 1).toUpperCase() + var[1].substring(1) + "Controller";
		String action; 
		if(var.length <=2)
			action = "loginForm";
		else
			action = var[2];
		
		//response.getWriter().println(controller + "  " + action);
		try {
			Class clazz = Class.forName(pkg + "." + controller);
			Object object = clazz.newInstance();
			java.lang.reflect.Method method = clazz.getMethod(action, HttpServletRequest.class,HttpServletResponse.class);
			method.invoke(object, request, response);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException| SecurityException | InstantiationException | ClassNotFoundException  e) {
			e.printStackTrace();
        	HttpSession session = request.getSession();
        	session.setAttribute("error msg", e.getMessage());
			getServletContext().getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
			
		}
        catch (Exception e) {
        	e.printStackTrace();
        	HttpSession session = request.getSession();
        	session.setAttribute("error msg", e.getMessage());
        	getServletContext().getRequestDispatcher("/ErrorPage.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
