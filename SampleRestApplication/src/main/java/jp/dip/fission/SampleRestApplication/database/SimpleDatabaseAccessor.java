package jp.dip.fission.SampleRestApplication.database;

import java.util.List;

public interface SimpleDatabaseAccessor<T> {
	public T doFind(Class<T> clazz);
	public List<T> doSelect(Class<T> clazz);
	public long doQuery(Class<T> clazz);
}
