package net.tnktoys.ZipAddress.database.table;

import java.util.List;

public abstract class AbstractTable {
	public <T> List<T> select() {
		return null;
	}
	
	public <T> T get() {
		return null;
	}
	
	public int insert() {
		return 0;
	}
	public int update() {
		return 0;
	}
	public int delete() {
		return 0;
	}
	
	public boolean create() {
		return false;
	}

	public boolean drop() {
		return false;
	}

	public boolean truncate() {
		return false;
	}
}
