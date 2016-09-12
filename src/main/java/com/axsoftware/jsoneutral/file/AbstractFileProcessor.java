package com.axsoftware.jsoneutral.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.axsoftware.jsoneutral.file.exception.FileJsonException;
import com.axsoftware.jsoneutral.file.exception.FileJsonException.Type;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public abstract class AbstractFileProcessor {

	private static final String ISO = "ISO8859-1";

	protected InputStreamReader convert(final File file) {
		try {
			return new InputStreamReader(new FileInputStream(file), ISO);
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			throw new FileJsonException(Type.ERROR_CONVERT_DATA_FILE);
		}
	}

	protected Map<String, DataRecord> getMetadataFromJsonConfig(final String configFile) {
		InputStream stream = getClass().getClassLoader().getResourceAsStream(configFile);
		if (stream == null) {
			stream = getClass().getResourceAsStream(configFile);
		}
		return getMetadataFromJsonConfig(stream);
	}

	@SuppressWarnings("deprecation")
	protected Map<String, DataRecord> getMetadataFromJsonConfig(InputStream inputStream) {
		
		final ObjectMapper mapper = new ObjectMapper();
		final Map<String, DataRecord> mapMetadata = new HashMap<String, DataRecord>();

		Collection<DataRecord> metadatas = null;
		try {
			metadatas = mapper.readValue(inputStream, TypeFactory.defaultInstance().constructParametricType(Collection.class, DataRecord.class));
			for (DataRecord lineMetadata : metadatas) {
				mapMetadata.put(lineMetadata.getId(), lineMetadata);
			}
		} catch (IOException e) {
			throw new FileJsonException(Type.FILE_PARSER_ERROR);
		}

		return mapMetadata;
	}
	
}