/**
 *
 */
package jp.dip.fission.database;

import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.dip.fission.annotation.Column;

/**
 * @author fission
 *
 */
abstract class AbstractDBConnection implements DBConnection {

	private Connection connection;

	protected AbstractDBConnection(Connection connection) throws IOException, ClassNotFoundException, DBException, SQLException {
		this.connection = connection;
		this.connection.setAutoCommit(true);
	}

//	// create database & create table
//	public abstract boolean createDatabase();
//
//	// insert data
//	public abstract boolean loadDatabase();
//
//	// truncate data
//	public boolean clearDatabase() {
//		return true;
//	}
//
//	// drop database
//	public abstract boolean dropDatabase();

	public abstract void addRange(String query, Map<String, Object> params, Integer offset, Integer limit);

	@Override
	public int count(String name,String query, Map<String, Object> params) throws SQLException, InstantiationException, IllegalAccessException {
		List<Object> paramList = new ArrayList<Object>();
		Pattern pattern = Pattern.compile("/#[\\0x20-0x7F]+?[\\0x20-0x7F]#/",Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while (matcher.find()) {
			int start = matcher.start();
			int end   = matcher.end();
			Object param = params.get(query.substring(start+2,end-2).trim());
			query = query.substring(0,start) + "?" + query.substring(end,query.length());
			paramList.add(params.get(param));
		}
		PreparedStatement stmt = this.connection.prepareStatement(query);
		setParameter(stmt, paramList);
		ResultSet rs = stmt.executeQuery();
		return rs.getInt(name);
	}

	public <T>List<T> select(Class<T> clazz,String query,Map<String,Object> params) throws SQLException, InstantiationException, IllegalAccessException {
		return select(clazz,query,params,null,null);
	}

	public <T>List<T> select(Class<T> clazz,String query,Map<String,Object> params,Integer offset,Integer limit) throws SQLException, InstantiationException, IllegalAccessException {
		List<T> resultList = new ArrayList<T>();
		List<Object> paramList = new ArrayList<Object>();
		// add offset and limit
		addRange(query,params,offset,limit);
		Pattern pattern = Pattern.compile("/#[\\0x20-0x7F]+?[\\0x20-0x7F]#/",Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while (matcher.find()) {
			int start = matcher.start();
			int end   = matcher.end();
			Object param = params.get(query.substring(start+2,end-2).trim());
			query = query.substring(0,start) + "?" + query.substring(end,query.length());
			paramList.add(params.get(param));
		}
		PreparedStatement stmt = this.connection.prepareStatement(query);
		setParameter(stmt, paramList);
		ResultSet rs = stmt.executeQuery();
		ResultSetMetaData meta = rs.getMetaData();
		Map<String,ColumnInfo> columnMap = new HashMap<String,ColumnInfo>();
		int columnLength = meta.getColumnCount();
		for (int i = 0 ; i < columnLength ; i++) {
			ColumnInfo columnInfo = new ColumnInfo();
			columnInfo.setColumnName(meta.getColumnLabel(i));
			columnInfo.setColumnType(meta.getColumnTypeName(i));
			columnInfo.setColumnClass(meta.getColumnClassName(i));
			columnMap.put(columnInfo.getColumnName(), columnInfo);
		}
		while(rs.next()) {
			T entity = clazz.newInstance();
			Field[] fields = clazz.getFields();
			for (Field field : fields) {
				ColumnInfo columnInfo = columnMap.get(field.getAnnotation(Column.class).name());
				if (columnInfo != null) {
					if (String.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getString(columnInfo.getColumnName()));
					} else if (Integer.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getInt(columnInfo.getColumnName()));
					} else if (Double.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getDouble(columnInfo.getColumnName()));
					} else if (Long.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getLong(columnInfo.getColumnName()));
					} else if (Float.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getFloat(columnInfo.getColumnName()));
					} else if (Short.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getShort(columnInfo.getColumnName()));
					} else if (Byte.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getByte(columnInfo.getColumnName()));
					} else if (Boolean.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getBoolean(columnInfo.getColumnName()));
					} else if (Date.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getDate(columnInfo.getColumnName()));
					} else if (Timestamp.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getTimestamp(columnInfo.getColumnName()));
					} else if (Object.class.getName().equals(columnInfo.getColumnClass())) {
						field.set(entity, rs.getObject(columnInfo.getColumnName()));
					}
				}
			}
			resultList.add(entity);
		}
		return resultList;
	}

	public int doQuery(String query,Map<String,Object> params) throws SQLException {
		List<Object> paramList = new ArrayList<Object>();
		Pattern pattern = Pattern.compile("/#[\\0x20-0x7F]+?[\\0x20-0x7F]#/",Pattern.DOTALL);
		Matcher matcher = pattern.matcher(query);
		while (matcher.find()) {
			int start = matcher.start();
			int end   = matcher.end();
			Object param = params.get(query.substring(start+2,end-2).trim());
			query = query.substring(0,start) + "?" + query.substring(end,query.length());
			paramList.add(params.get(param));
		}
		PreparedStatement stmt = this.connection.prepareStatement(query);
		setParameter(stmt, paramList);
		return stmt.executeUpdate();
	}

	public boolean isConnect() {
		if(this.connection == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean isClosed() throws SQLException {
		if (this.connection == null) {
			return true;
		} else {
			return this.connection.isClosed();
		}
	}

	public boolean close() throws SQLException {
		if(this.isClosed()) {
			return true;
		} else {
			try {
				this.connection.close();
			} catch (SQLException e) {
				throw e;
			} finally {
				this.connection = null;
			}
		}
		return true;
	}

	public void transactionStart() throws SQLException, DBException {
		if (!this.connection.getAutoCommit()) {
			throw new DBException("transaction already start");
		}
		if (isClosed()) {
			throw new DBException("already closed");
		} else {
			this.connection.setAutoCommit(false);
		}
	}

	public void transactionRollback() throws DBException, SQLException {
		if (this.connection.getAutoCommit()) {
			throw new DBException("transaction disabled");
		}
		if (isClosed()) {
			throw new DBException("already closed");
		} else {
			this.connection.rollback();
		}
	}

	public void transactionCommit() throws DBException, SQLException {
		if (this.connection.getAutoCommit()) {
			throw new DBException("transaction disabled");
		}
		if (isClosed()) {
			throw new DBException("already closed");
		} else {
			this.connection.commit();
		}
	}

	public void transactionEnd() throws SQLException, DBException {
		if (this.connection.getAutoCommit()) {
			throw new DBException("transaction already end");
		}
		if (isClosed()) {
			throw new DBException("already closed");
		} else {
			this.connection.commit();
			this.connection.setAutoCommit(true);
		}
	}

	private void setParameter (PreparedStatement stmt,List<Object> paramList) throws SQLException {
		for (int i = 0 ; i < paramList.size() ; i ++) {
			Object param = paramList.get(i);
			if (param instanceof Arrays) {
				List<Object> list = Arrays.asList(param);
				for (Object obj : list) {
					if (obj instanceof String) {
						stmt.setString(i + 1, (String)paramList.get(i));
					} else if (obj instanceof Integer) {
						stmt.setInt(i + 1, (Integer)paramList.get(i));
					} else if (obj instanceof Double) {
						stmt.setDouble(i + 1, (Double)paramList.get(i));
					} else if (param instanceof Long) {
						stmt.setLong(i + 1, (Long)paramList.get(i));
					} else if (param instanceof Float) {
						stmt.setFloat(i + 1, (Float)paramList.get(i));
					} else if (param instanceof Short) {
						stmt.setShort(i + 1, (Short)paramList.get(i));
					} else if (param instanceof Byte) {
						stmt.setByte(i + 1, (Byte)paramList.get(i));
					} else if (param instanceof Boolean) {
						stmt.setBoolean(i + 1, (Boolean)paramList.get(i));
					} else if (param instanceof Date) {
						stmt.setDate(i + 1, (Date)paramList.get(i));
					} else if (param instanceof Timestamp) {
						stmt.setTimestamp(i + 1, (Timestamp)paramList.get(i));
					} else if (param instanceof Object) {
						stmt.setObject(i + 1, (Object)paramList.get(i));
					} else if (param instanceof Arrays) {
						stmt.setObject(i + 1, (Object)paramList.get(i));
					}
				}
			}
			if (param instanceof String) {
				stmt.setString(i + 1, (String)paramList.get(i));
			} else if (param instanceof Integer) {
				stmt.setInt(i + 1, (Integer)paramList.get(i));
			} else if (param instanceof Double) {
				stmt.setDouble(i + 1, (Double)paramList.get(i));
			} else if (param instanceof Long) {
				stmt.setLong(i + 1, (Long)paramList.get(i));
			} else if (param instanceof Float) {
				stmt.setFloat(i + 1, (Float)paramList.get(i));
			} else if (param instanceof Short) {
				stmt.setShort(i + 1, (Short)paramList.get(i));
			} else if (param instanceof Byte) {
				stmt.setByte(i + 1, (Byte)paramList.get(i));
			} else if (param instanceof Boolean) {
				stmt.setBoolean(i + 1, (Boolean)paramList.get(i));
			} else if (param instanceof Date) {
				stmt.setDate(i + 1, (Date)paramList.get(i));
			} else if (param instanceof Timestamp) {
				stmt.setTimestamp(i + 1, (Timestamp)paramList.get(i));
			} else if (param instanceof Object) {
				stmt.setObject(i + 1, (Object)paramList.get(i));
			} else if (param instanceof Arrays) {
				stmt.setObject(i + 1, (Object)paramList.get(i));
			}
		}
	}
}
