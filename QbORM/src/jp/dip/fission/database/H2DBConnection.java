package jp.dip.fission.database;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

class H2DBConnection extends AbstractDBConnection {

	H2DBConnection(Connection connection) throws IOException,
			ClassNotFoundException, DBException, SQLException {
		super(connection);
	}

//	@Override
//	public boolean createDatabase() {
//		return false;
//	}
//
//	@Override
//	public boolean loadDatabase() {
//		return false;
//	}
//
//	@Override
//	public boolean dropDatabase() {
//		return false;
//	}

	@Override
	public void addRange(String query,Map<String,Object> params,Integer offset,Integer limit) {
		StringBuffer buf = new StringBuffer();
		buf.append(query);
		if (offset != null) {
			buf.append(" offset /#offset#/ ");
			params.put("offset", offset);
		}
		if (limit != null) {
			buf.append(" limit /#limit#/ ");
			params.put("limit", limit);
		}
	}
}
