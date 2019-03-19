package net.tnktoys.ZipAddress.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.server.mvc.Viewable;

import net.tnk_toys.ZipCodeLib.ZipCodeException;
import net.tnk_toys.ZipCodeLib.ZipCodeManager;
import net.tnk_toys.ZipCodeLib.entity.ZipEntity;
import net.tnktoys.ZipAddress.tools.DataConnector;

@Path("/")
public class ZipSearch {

	@GET
	@Path("/code/{zipAddress}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ZipEntity> getCode(@PathParam("zipAddress") String zipAddress) {
		System.out.println("name");
		
		List<ZipEntity> results = new ArrayList<>();
		ZipCodeManager tools = DataConnector.getInstance().getZipCodeManager();
		try {
			String zip = tools.sanitizeZipCode(zipAddress);
			tools.updateZipCode();
			List<ZipEntity> entityList = tools.searchZipCode(zip,ZipCodeManager.SearchType.CODE);
			entityList.forEach(entity -> {
				results.add(entity);
				System.out.println(entity.getCodeZip()+"-"+entity.getNamePrefecturesKanji()+entity.getNameCityKanji()+entity.getNameTownAreaKanji());
			});
		} catch (ZipCodeException e) {
			e.printStackTrace();
		}
		return results;
	}

	@GET
	@Path("/name/{zipName}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ZipEntity> getName(@PathParam("zipName") String zipName) {
		System.out.println("name");
		List<ZipEntity> results = new ArrayList<>();
		ZipCodeManager tools = DataConnector.getInstance().getZipCodeManager();
		try {
			String zip = tools.sanitizeZipName(zipName);
			tools.updateZipCode();
			List<ZipEntity> entityList = tools.searchZipCode(zip,ZipCodeManager.SearchType.NAME);
			entityList.forEach(entity -> {
				results.add(entity);
				System.out.println(entity.getCodeZip()+"-"+entity.getNamePrefecturesKanji()+entity.getNameCityKanji()+entity.getNameTownAreaKanji());
			});
		} catch (ZipCodeException e) {
			e.printStackTrace();
		}
		return results;
	}

	@GET
	@Path("/zip")
	public Viewable getView() {
		Map<String, String> model = new HashMap<>();
		model.put("test", "aaa");
		return new Viewable("/zip.vm",model);
	}
}
