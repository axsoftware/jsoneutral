package com.jsoneutral.file.util;

import java.lang.reflect.Field;

public class ReflectionUtil {

	public static Field findField(final Class<?> clazz, final String property) {
		final Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (field.getName().equals(property)) {
				return field;
			}
		}
		throw new IllegalArgumentException(String.format("Property not found", property));
	}

	public static Object readField(Field field, Object pojo, boolean b) {
		field.setAccessible(true);
		try {
			return field.get(pojo);
		} catch (IllegalArgumentException | IllegalAccessException e) {
		}
		return "";
	}
}
