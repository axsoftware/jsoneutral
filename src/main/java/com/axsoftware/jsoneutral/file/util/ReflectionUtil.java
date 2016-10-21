package com.axsoftware.jsoneutral.file.util;

import java.lang.reflect.Field;
import java.util.stream.Stream;

public class ReflectionUtil {

	
	/**
	 * Use getFields(final Class<?> clazz, Field[] fields) method
	 * Get all fields in object 
	 * @param clazz
	 * @return
	 */
	public static Field[] getObjectFields(final Class<?> clazz){
		return getFields(clazz, null);
	}
	
	/**
	 * Get all fields in object 
	 * @param  clazz
	 * @param  fields
	 * @return List<Field[]>
	 */
	public static Field[] getFields(final Class<?> clazz, Field[] fields){
		
		if(fields == null){
			fields = new Field[]{};
		}
		
		if(Object.class.equals(clazz)){
			return fields; 
		}
		
		final Field[] objectFields = clazz.getDeclaredFields();
		
		fields = Stream.of(objectFields, fields).flatMap(Stream::of).toArray(Field[]::new); 
		
		return getFields(clazz.getSuperclass(), fields);
	}

	/**
	 * Get field property through object class
	 * @param  clazz
	 * @param  property
	 * @return Field
	 */
	public static Field findField(final Class<?> clazz, final String property) {
		final Field[] fields = getObjectFields(clazz);
		return findField(property, fields);
	}

	public static Field findField(final String property, final Field[] fields) {
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
