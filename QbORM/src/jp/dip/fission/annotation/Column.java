package jp.dip.fission.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
	String name();
	String type() default "";
	boolean isKey() default false;
	int keyOrder() default 0;
	int maxLength() default 0;
	boolean notNull() default false;
}
