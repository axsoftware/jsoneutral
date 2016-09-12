package com.jsoneutral.file.exception;

public class FileJsonException extends RuntimeException {

	private static final long serialVersionUID = -815193978864787781L;

	public enum Type {
		ERROR_CONVERT_DATA_FILE,
		FILE_JSON_DATA_ERROR, 
		FILE_PARSER_ERROR, 
		FILE_DATA_ERROR, 
		FILE_ERROR_SET_DATA_VALUE, 
		FILE_ERROR_CLASS, 
		DATA_FILE_CONFIG_NOT_FOUND, 
		ERROR_GENERATE_FILE
	}

	public FileJsonException(Exception cause) {
		super(cause);
	}

	public FileJsonException(Type exceptionType) {
		super(exceptionType.toString());
	}
}
