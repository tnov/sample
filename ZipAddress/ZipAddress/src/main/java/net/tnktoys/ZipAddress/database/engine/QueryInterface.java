package net.tnktoys.ZipAddress.database.engine;

public interface QueryInterface {
	public QueryInterface addParam(Class clazz,Object obj);
	public StringBuilder build();
}
