package jp.dip.fission.database;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface DBConnection {
//	public boolean createDatabase();
//
//	public boolean loadDatabase();
//
//	public boolean clearDatabase();
//
//	// drop database
//	public boolean dropDatabase();

	public int count(String name,String query,Map<String,Object> params) throws SQLException, InstantiationException, IllegalAccessException;

	public <T>List<T> select(Class<T> clazz,String query,Map<String,Object> params) throws SQLException, InstantiationException, IllegalAccessException;

	public void addRange(String query,Map<String,Object> params,Integer offset,Integer limit);

	public <T>List<T> select(Class<T> clazz,String query,Map<String,Object> params,Integer offset,Integer limit) throws SQLException, InstantiationException, IllegalAccessException;

	public int doQuery(String query,Map<String,Object> params) throws SQLException;

	public boolean isConnect();

	public boolean isClosed() throws SQLException;

	public boolean close() throws SQLException;

	public void transactionStart() throws SQLException, DBException;

	public void transactionRollback() throws DBException, SQLException;

	public void transactionCommit() throws DBException, SQLException;

	public void transactionEnd() throws SQLException, DBException;
}
