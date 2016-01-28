package jp.dip.fission.database;

public class DBException extends Exception {
	public DBException() {
		super();
	}
	public DBException(String msg) {
		super(msg);
	}
	public DBException(Throwable throwable) {
		super(throwable);
	}
	public DBException(String msg,Throwable throwable) {
		super(msg,throwable);
	}
}
