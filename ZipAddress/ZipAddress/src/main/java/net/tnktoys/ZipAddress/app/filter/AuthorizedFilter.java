package net.tnktoys.ZipAddress.app.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;

public class AuthorizedFilter implements ContainerRequestFilter, ContainerResponseFilter {

	@Context
	private HttpServletRequest request;
	@Context
	private HttpServletResponse response;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("request auth filter");
		HttpSession session = request.getSession();
		if (!isAuthorized(session)) {
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		System.out.println("response auth filter");
		HttpSession session = request.getSession();
		if (!isAuthorized(session)) {
			response.sendRedirect("/login");
		}
	}
	
	private boolean isAuthorized(HttpSession session) {
		long lastAccessedTime = session.getLastAccessedTime();
		int  interval         = session.getMaxInactiveInterval();
		
		if (session.isNew()) {
			return false;
		}
		if (System.currentTimeMillis() > lastAccessedTime+interval) {
			return false;
		}
		if (session.getAttribute("LOGIN") == null) {
			return false;
		}
		return true;
	}
}
