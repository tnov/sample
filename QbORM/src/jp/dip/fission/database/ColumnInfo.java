package jp.dip.fission.database;

class ColumnInfo {
	private String columnName;
	private String columnType;
	private String columnClass;
	String getColumnName() {
		return columnName;
	}
	void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	String getColumnType() {
		return columnType;
	}
	void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	String getColumnClass() {
		return columnClass;
	}
	void setColumnClass(String columnClass) {
		this.columnClass = columnClass;
	}
}
