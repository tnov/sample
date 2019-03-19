package net.tnk_toys.ZipCodeLib;

public class ZipCodeException extends Exception {
	public static final int CODE_SUCCEED = 0;
	public static final int CODE_INITIALIZE_FAILURE = 1;
	public static final int CODE_ZIP_CODE_UPDATING = 2;
	public static final int CODE_EMPTY_ZIP_CODE = 3;
	public static final int CODE_LESS_MIN_LENGTH = 4;
	public static final int CODE_OVER_MAX_LENGTH = 5;
	public static final int CODE_CONTAIN_NON_NUMERIC_ZIP_CODE = 6;

	public static final String MESSAGE_INITIALIZE_FAILURE = "Can't initialize ZipCodeTool Instance";
	public static final String MESSAGE_ZIP_CODE_UPDATING = "ZipCode updating";
	public static final String MESSAGE_EMPTY_ZIP_CODE = "ZipCode is empty";
	public static final String MESSAGE_LESS_MIN_LENGTH = "ZipCode less minlength(3)";
	public static final String MESSAGE_OVER_MAX_LENGTH = "ZipCode over maxlength(7)";
	public static final String MESSAGE_CONTAIN_NON_NUMERIC_ZIP_CODE = "ZipCode contains a non-numeric";
	
	public static final String[] messages = {
		 ""
		,MESSAGE_INITIALIZE_FAILURE
		,MESSAGE_ZIP_CODE_UPDATING
		,MESSAGE_EMPTY_ZIP_CODE
		,MESSAGE_LESS_MIN_LENGTH
		,MESSAGE_OVER_MAX_LENGTH
		,MESSAGE_CONTAIN_NON_NUMERIC_ZIP_CODE
	};
	
	private int errorCode = CODE_SUCCEED;
	
	public ZipCodeException() {
		super();
	}
	
	public ZipCodeException(int code) {
		super(messages[code]);
		errorCode = code;
	}
	
	public ZipCodeException(int code,Throwable cause) {
		super(messages[code],cause);
		errorCode = code;
	}
	
	public ZipCodeException(Throwable cause) {
		super(cause);
	}
	
    protected ZipCodeException(int code, Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
    	super(messages[code], cause, enableSuppression, writableStackTrace);
		errorCode = code;
    }
    
    public int getErrorCode() {
    	return errorCode;
    }
}
