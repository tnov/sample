package jp.dip.fission.SampleRestApplication;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.jsonp.JsonProcessingFeature;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/rest")
public class AppConfig extends ResourceConfig {
	public AppConfig() {
		register(JsonProcessingFeature.class);
		packages(true, this.getClass().getPackage().getName());
	}
}
