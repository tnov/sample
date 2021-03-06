package jp.co.helloweb;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="HelloWebWorld",value="/")
public class HelloWebWorld extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("HelloWebWorld");
//		resp.setContentType("text/html; charset=UTF-8");
//		resp.getWriter().write("<html><head><title>HelloWebWorld</title></head><body>HelloWebWorld</body></html>");
		RequestDispatcher dispatcher = req.getRequestDispatcher("/WEB-INF/jsp/helloweb.jsp");
		dispatcher.forward(req,resp);
	}
}
