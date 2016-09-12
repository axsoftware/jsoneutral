package com.axsoftware.jsoneutral.file.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;

import com.axsoftware.jsoneutral.file.AbstractFileProcessor;
import com.axsoftware.jsoneutral.file.DataRecord;
import com.axsoftware.jsoneutral.file.JsonTextFile;
import com.axsoftware.jsoneutral.file.exception.FileJsonException;
import com.axsoftware.jsoneutral.file.exception.FileJsonException.Type;


public class FileJsonReaderImpl extends AbstractFileProcessor implements FileJsonReader {

	@Override
	public void execute(File dataFile, String configFile, FileJsonReaderProcessor textFileProcessor) {
		Map<String, DataRecord> linesMetadata = getMetadataFromJsonConfig(configFile);
		execute(dataFile, textFileProcessor, linesMetadata);
	}

	private void execute(File dataFile, FileJsonReaderProcessor textFileProcessor, Map<String, DataRecord> linesMetadata) {
		InputStreamReader dataInputStreamReader = convert(dataFile);

		try (BufferedReader bufferedReader = new BufferedReader(dataInputStreamReader)) {
			JsonTextFile stf = new JsonTextFile(bufferedReader, linesMetadata);
			textFileProcessor.process(stf);
		} catch (IOException e) {
			throw new FileJsonException(Type.FILE_DATA_ERROR);
		}
	}

}
