package com.jsoneutral.file;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import com.jsoneutral.file.exception.FileJsonException;
import com.jsoneutral.file.exception.FileJsonException.Type;


public class JsonDataLine {

	private final String line;
	private final Map<String, DataRecord> linesMetadata;

	public JsonDataLine(Map<String, DataRecord> linesMetadata, String line) {
		this.linesMetadata = linesMetadata;
		this.line = line;
	}

	public String get(int start, int end) {
		if (start > end || start < 1) {
			throw new FileJsonException(Type.FILE_JSON_DATA_ERROR);
		}

		if (start > line.length()) {
			return null;
		}

		if (end > line.length()) {
			return line.substring(start - 1);
		}

		return line.substring(start - 1, end);
	}

	public <T> T deserialize(String type, Class<T> clazz) {
		T instance = null;
		try {
			instance = clazz.newInstance();
		} catch (Exception e) {
			throw new FileJsonException(Type.FILE_ERROR_CLASS);
		}

		DataRecord lineMetadata = linesMetadata.get(type);
		if (lineMetadata == null) {
			throw new FileJsonException(Type.DATA_FILE_CONFIG_NOT_FOUND);
		}

		String fieldValue = null;
		for (JsonMetadata fragmentMetadata : lineMetadata.getFragmentsMetadata()) {
			fieldValue = get(fragmentMetadata.getStart(), fragmentMetadata.getEnd());
			if (fieldValue == null) {
				continue;
			}
			
			try {
				final Field field = instance.getClass().getDeclaredField(fragmentMetadata.getKey());
				field.setAccessible(true);
				field.set(instance, fieldValue.trim());
				
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				throw new FileJsonException(Type.FILE_ERROR_SET_DATA_VALUE);
			}

		}
		return instance;
	}

	public Map<String, String> deserialize(String type) {
		Map<String, String> map = new HashMap<>();

		DataRecord lineMetadata = linesMetadata.get(type);
		if (lineMetadata == null) {
			throw new FileJsonException(Type.DATA_FILE_CONFIG_NOT_FOUND);
		}

		for (JsonMetadata fragmentMetadata : lineMetadata.getFragmentsMetadata()) {
			map.put(fragmentMetadata.getKey(), get(fragmentMetadata.getStart(), fragmentMetadata.getEnd()));
		}
		return map;
	}

	public String getLine() {
		return line;
	}

}