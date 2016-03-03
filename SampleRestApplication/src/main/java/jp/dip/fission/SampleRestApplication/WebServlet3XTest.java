package jp.dip.fission.SampleRestApplication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="WebServlet3XTest",urlPatterns={"/WebServlet3XTest/*"})
public class WebServlet3XTest extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//		super.doGet(req, resp);
		resp.getWriter().print("it is Servlet 3.0. ");
	}

}
