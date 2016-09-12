package com.axsoftware.jsoneutral.file.reader;

import java.io.IOException;

import com.axsoftware.jsoneutral.file.JsonTextFile;

public interface FileJsonReaderProcessor {

	void process(JsonTextFile structuredTextFile) throws IOException;

}
