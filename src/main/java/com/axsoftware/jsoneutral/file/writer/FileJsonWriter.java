
package com.axsoftware.jsoneutral.file.writer;

import java.io.File;
import java.util.Collection;

public interface FileJsonWriter {

	Collection<String> execute(Collection<?> pojos, String configFilePath, String configId);

	String execute(Object data, String configFilePath, String configId);

	Collection<String> execute(Collection<?> pojos, String configFilePath, String configId, FileJsonWriterProcessor lineProcessor);

	String execute(Object data, String configFilePath, String configId, FileJsonWriterProcessor lineProcessor);

	File execute(Collection<?> pojos, String configFilePath, String configId, String fileName);

	File execute(Collection<?> pojos, String configFilePath, String configId, FileJsonWriterProcessor lineProcessor, String fileName);

	File generateFile(Collection<String> lines, String filename) throws Exception;

}