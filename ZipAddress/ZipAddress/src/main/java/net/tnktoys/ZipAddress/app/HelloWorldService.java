package net.tnktoys.ZipAddress.app;

import javax.ws.rs.CookieParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;

import net.tnktoys.ZipAddress.entity.SampleEntity;

@Path("/sample")
public interface HelloWorldService {

    // ex) http://192.168.55.10:8080/jersey2-sample/rest/sample/hello/HELLO
    @GET
    @Path("/hello/{message}")
    public String sayHello(@PathParam("message") String message);

    // ex) http://192.168.55.10:8080/jersey2-sample/rest/sample/path_param/4+7/
    @GET
    @Path("/path_param/{op1}+{op2}/")
    public String pathParamSample(@PathParam("op1") int op1, @PathParam("op2") int op2);

    // ex) http://192.168.55.10:8080/jersey2-sample/rest/sample/matrix_param/HOGE;attr=fuga
    @GET
    @Path("/matrix_param/{message}")
    public String matrixParamSample(@PathParam("message") String message, @MatrixParam("attr") String attr);

    // ex) http://192.168.55.10:8080/jersey2-sample/rest/sample/query_param/HOGE?param1=hoge
    @GET
    @Path("/query_param/{message}")
    public String queryParamSample(@PathParam("message") String message, @QueryParam("param1") String param1);

    // ex) http://192.168.55.10:8080/jersey2-sample/post_test.jsp
    @POST
    @Path("/form_param/")
    public String formParamSample(@FormParam("text1") String text);

    // ex) http://192.168.55.10:8080/jersey2-sample/rest/sample/header_param/
    @GET
    @Path("/header_param/")
    public String headerParamSample(@HeaderParam("User-Agent") String userAgent);

    // ex) http://192.168.55.10:8080/jersey2-sample/rest/sample/cookie_param/
    @GET
    @Path("/cookie_param/")
    public String cookieParamSample(@CookieParam("JSESSIONID") Cookie jsessionId);

    // ex) http://192.168.55.10:8080/jersey2-sample/rest/sample/test/xml
    @GET
    @Path("/test/xml")
    @Produces("application/xml")
    public SampleEntity getXml();

    // ex) http://192.168.55.10:8080/jersey2-sample/rest/sample/test/json
    @GET
    @Path("/test/json")
    @Produces("application/json")
    public SampleEntity getJson();
}
