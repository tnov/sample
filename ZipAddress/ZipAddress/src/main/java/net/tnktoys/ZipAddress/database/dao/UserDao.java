package net.tnktoys.ZipAddress.database.dao;

import java.util.List;

import net.tnktoys.ZipAddress.database.annotation.Parameter;
import net.tnktoys.ZipAddress.database.annotation.Query;
import net.tnktoys.ZipAddress.database.table.User;

public interface UserDao extends AbstractDao<User, User> {

	@Query(query="select * from dual where id = /* id */ ")
	public List<User> doSelect(@Parameter(name="id") String id);
}
