package com.jsoneutral.file.reader;

import java.io.IOException;

import com.jsoneutral.file.JsonTextFile;

public interface FileJsonReaderProcessor {

	void process(JsonTextFile structuredTextFile) throws IOException;

}
