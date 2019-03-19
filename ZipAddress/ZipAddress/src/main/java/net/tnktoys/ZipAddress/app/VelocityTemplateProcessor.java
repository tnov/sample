package net.tnktoys.ZipAddress.app;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.glassfish.jersey.server.mvc.Viewable;
import org.glassfish.jersey.server.mvc.spi.TemplateProcessor;

public class VelocityTemplateProcessor implements TemplateProcessor<String> {


	@Context
	private ServletContext servletContext;

	public VelocityTemplateProcessor() {
    }

	@Override
	public String resolve(String name, MediaType mediaType) {
		return name;
	}

	@Override
	public void writeTo(String templateReference, Viewable viewable, MediaType mediaType,
			MultivaluedMap<String, Object> httpHeaders, OutputStream out) throws IOException {
		// Velocityの初期化
		VelocityEngine engine = new VelocityEngine();
		engine.setApplicationAttribute("javax.servlet.ServletContext", servletContext);
		engine.init(this.getClass().getResource("/velocity.properties").getPath());
		VelocityContext context = new VelocityContext();
		Map<String,String> model = (Map<String, String>) viewable.getModel();
		Set<String> set = model.keySet();
		for (String key : set) {
			context.put(key, model.get(key));
		}
		Template template = engine.getTemplate(templateReference,StandardCharsets.UTF_8.name());
		Writer writer =  new OutputStreamWriter(out,StandardCharsets.UTF_8);
		template.merge(context, writer);
		writer.flush();
	}
}
