package net.tnktoys.ZipAddress.database.engine;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.tnktoys.ZipAddress.database.table.User;

public class QueryBuilderMain {

	private static Logger logger = Logger.getLogger(QueryBuilderMain.class.getName());
	public static void main(String[] args) {
		System.out.println(select());
		User user = new User();
		user.id = "1";
		HashMap<String,Object> param = new HashMap<String,Object>();
		param.put("id", user.id);
		List<User> userList = QueryBuilder
								.select(User.class)
								.setQuery("select * from user where id = /* id */ ")
								.setParameter(param)
								.execute();
		QueryBuilder.select(null).innerJoin(null,null);
		logger.log(Level.INFO, "success");
	}
	
	public static String select() {
		QueryBuilder query = new QueryBuilder();
		String str = "select * from user where id = /* id */";
		return query.bind(str).toString();
	}
}
