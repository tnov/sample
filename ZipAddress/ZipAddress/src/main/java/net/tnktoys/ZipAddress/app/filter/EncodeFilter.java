package net.tnktoys.ZipAddress.app.filter;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Context;

public class EncodeFilter implements ContainerRequestFilter, ContainerResponseFilter {
    /** エンコード */
    private final static String encoding = "UTF-8";

	@Context
	private HttpServletRequest request;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		System.out.println("request encode filter");
		request.setCharacterEncoding(encoding);
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		System.out.println("response encode filter");
		request.setCharacterEncoding(encoding);
	}

}
