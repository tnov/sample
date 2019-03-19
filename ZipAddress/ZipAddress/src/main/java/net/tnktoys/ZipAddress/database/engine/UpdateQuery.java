package net.tnktoys.ZipAddress.database.engine;

import java.util.List;

public class UpdateQuery<T> {
	
	T entity = null;
	
	public UpdateQuery(T entity) {
		this.entity = entity;
	}
	
	public void setParam() {
		
	}
	
	public List<T> execute() {
		return null;
	}
}
