package net.tnktoys.ZipAddress.database.dao;

import java.util.List;

public interface AbstractDao<T,V> {
	
	public T get(V v);
	
	public List<T> select(V v);
	
	public long insert(V v);
	
	public long update(V v);
	
	public long delete(V v);
	
}
