package jp.dip.fission.SampleRestApplication;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/rest")
public class AppConfig extends ResourceConfig {
	public AppConfig() {
		  packages(true, this.getClass().getPackage().getName());
	}
}
