package com.axsoftware.jsoneutral.file.reader;

import java.io.File;

public interface FileJsonReader {

	void execute(File dataFile, String configFilePath, FileJsonReaderProcessor textFileProcessor);

}
