package jp.dip.fission.SampleRestApplication.app.sso;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jp.dip.fission.SampleRestApplication.tools.PeriodGenerator;
import jp.dip.fission.SampleRestApplication.tools.TokenGenerator;

@Path("/sso")
public class SSOAuthorizedServer {

	@POST
	@Path("/authorized")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SSOAuthorizedEntity authorized(SSOAuthorizedEntity bean) {
		SSOAuthorizedEntity entity = new SSOAuthorizedEntity();
		entity.user = bean.user;
		entity.password = bean.password;
		entity.project = bean.project;


		entity.token = TokenGenerator.createToken();
		entity.period = PeriodGenerator.createPeriod();
		return entity;
	}

	@POST
	@Path("/check")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SSOAuthorizedEntity check(SSOAuthorizedEntity bean) {
		SSOAuthorizedEntity entity = new SSOAuthorizedEntity();
		entity.user = bean.user;
		entity.password = bean.password;
		entity.project = bean.project;


		entity.token = TokenGenerator.createToken();
		entity.period = PeriodGenerator.createPeriod();
		return entity;
	}

	@POST
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public SSOAuthorizedEntity update(SSOAuthorizedEntity bean) {
		SSOAuthorizedEntity entity = new SSOAuthorizedEntity();
		entity.user = bean.user;
		entity.password = bean.password;
		entity.project = bean.project;


		entity.token = TokenGenerator.createToken();
		entity.period = PeriodGenerator.createPeriod();
		return entity;
	}
}
