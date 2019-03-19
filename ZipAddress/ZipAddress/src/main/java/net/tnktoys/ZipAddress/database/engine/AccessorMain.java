package net.tnktoys.ZipAddress.database.engine;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.validation.constraints.NotNull;

import net.tnktoys.ZipAddress.database.annotation.Column;
import net.tnktoys.ZipAddress.database.annotation.Query;
import net.tnktoys.ZipAddress.database.annotation.Table;
import net.tnktoys.ZipAddress.database.dao.UserDao;
import net.tnktoys.ZipAddress.database.table.User;

public class AccessorMain {

	public static void main(String[] args) {
		System.out.println("main start");
		Annotation[] annotations = null;
		Class clazz = UserDao.class;
		System.out.println("class annotations");
		annotations = clazz.getAnnotations();
		for (Annotation annotation : annotations) {
			System.out.println(annotation.annotationType().toString());
			System.out.println("--" + annotation.annotationType().getName());
			System.out.println("--" + annotation.annotationType().getTypeName());
		}
		System.out.println("method annotations");
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			annotations = method.getAnnotations();
			for (Annotation annotation : annotations) {
				System.out.println(annotation.annotationType().toString());
				System.out.println("--" + annotation.annotationType().getName());
				System.out.println("--" + method.getAnnotation(Query.class).file());
				System.out.println("--" + method.getAnnotation(Query.class).query());
			}
		}
		User user = new User();
		doQuery(user);
		System.out.println("main end");
	}
	public static void doQuery(@NotNull User entity) {
		Class<?> clazz = entity.getClass();
		Table table = clazz.getAnnotation(Table.class);
		String schema = table.schema();
		String name = table.name();
		String comment = table.comment();
		System.out.println("schema:" + schema + " / name:" + name + " / comment:" + comment);
		Field[] fields = clazz.getFields();
		for (Field field : fields) {
			Column column = field.getAnnotation(Column.class);
			if (column != null) {
				String columnName = column.name();
				boolean notnull = column.notnull();
				int primary = column.primary();
				int number = column.number();
				int decimal = column.decimal();
				Class<?> type = column.type();
				String columnComment = column.comment();
				System.out.println("-- columnName:" + columnName + " / notnull:" + notnull + " / primary:" + primary + " / number:" + number + " / decimal:" + decimal + " / type:" + type + " / columnComment:" + columnComment);
			}
		}
	}
}
