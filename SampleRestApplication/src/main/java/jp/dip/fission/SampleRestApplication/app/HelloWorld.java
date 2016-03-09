package jp.dip.fission.SampleRestApplication.app;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;

import jp.dip.fission.SampleRestApplication.entity.SampleEntity;

@Path("/hello")
public class HelloWorld {

	@Inject
	private HttpServletRequest request;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getHelloWorld() {
		return "Hello Worldn!";
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public SampleEntity getSampleEntity(@QueryParam("name") String name) {
		SampleEntity entity = new SampleEntity();
		entity.name = "test";
		if (name != null) {
			entity.name = name;
		}
		System.out.println("param = " + request.getParameter("param"));
		return entity;
	}

	@GET
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SampleEntity postSampleEntity(SampleEntity bean) {
		SampleEntity entity = new SampleEntity();
		entity.name = "test";
		if (bean != null) {
			entity.name = bean.name;
		}
		return entity;
	}

	@GET
	@Path("/velocity")
	public Viewable helloVelocity() {
		Map<String, String> model = new HashMap<>();
		model.put("test", "test");
		return new Viewable("/hello.vm",model);
	}
}
