package jp.dip.fission.SampleRestApplication.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.poi.ss.formula.functions.T;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	String value();
	Class<T> type();
	int number();
	int decimal();
	int primary();
	boolean notnull();
	String comment();
}
