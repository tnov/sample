package net.tnktoys.ZipAddress.database.engine;

import java.util.HashMap;
import java.util.List;


public class SelectQuery<T> {
	
	Class<T> clazz = null;
	String query = null;
	
	// columns : name (and alias)
	List<String> select = null;
	// tables : table and type
	List<String> from = null;
	// wheres : key ope value and criteria
	List<String> where = null;
	
	HashMap<String,SelectQuery<T>> children = null;
	
	SelectQuery(Class<T> clazz) {
		this.clazz = clazz;
		this.children = new HashMap<String,SelectQuery<T>>();
	}
	
	public SelectQuery<T> setQuery(String query) {
		this.query = query;
		return this;
	}
	
	public SelectQuery<T> setParameter(HashMap<String,Object> parameter) {
		return this;
	}
	
	public List<T> execute() {
		return null;
	}
	
	public SelectQuery<T> addWhere(SelectQuery<T> selectQuery) {
		return this;
	}
	
	public SelectQuery<T> addWhere(QueryParts criteria) {
		return this;
	}
	
	public SelectQuery<T> innerJoin(SelectQuery<T> innerJoin,QueryParts criteria) {
		return this;
	}
	public SelectQuery<T> leftJoin(SelectQuery<T> leftJoin,QueryParts criteria) {
		return this;
	}
	public SelectQuery<T> rightJoin(SelectQuery<T> rightJoin,QueryParts criteria) {
		return this;
	}
	public SelectQuery<T> union(SelectQuery<T> union) {
		return this;
	}
	public SelectQuery<T> minus(SelectQuery<T> minus) {
		return this;
	}
}
