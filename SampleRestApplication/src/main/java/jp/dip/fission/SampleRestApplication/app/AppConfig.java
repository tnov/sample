package jp.dip.fission.SampleRestApplication.app;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.MvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;

@ApplicationPath("/rest")
public class AppConfig extends ResourceConfig {
	public AppConfig() {
		packages(true, this.getClass().getPackage().getName());
		property(ServletProperties.FILTER_FORWARD_ON_404, true);
		property(ServletProperties.FILTER_STATIC_CONTENT_REGEX, "/(css|jsl|html|vm)/.*");
		register(SampleFilter.class);
		register(JsonProcessingFeature.class);
		register(VelocityTemplateProcessor.class);
		register(MvcFeature.class);
	}
}
