package net.tnktoys.ZipAddress.database.engine;

import java.util.ArrayList;
import java.util.List;

public class QueryParts {

	List<QueryParts> select = new ArrayList<>();
	List<QueryParts> from = new ArrayList<>();
	List<QueryParts> where = new ArrayList<>();
	List<QueryParts> orderby = new ArrayList<>();
	List<QueryParts> groupby = new ArrayList<>();
	List<QueryParts> join = new ArrayList<>();
	
	List<QueryParts> queryList = new ArrayList<>();
	public String build() {
		StringBuilder sb = new StringBuilder();
		for (QueryParts query : queryList) {
			sb.append(query.build());
		}
		
		return sb.toString();
	}
}
