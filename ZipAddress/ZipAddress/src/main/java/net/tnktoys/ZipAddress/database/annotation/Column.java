package net.tnktoys.ZipAddress.database.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	String name();
	Class<? extends Object> type();
	int number();
	int decimal() default 0;
	int primary() default 0;
	boolean notnull() default false;
	String comment() default "";
}
