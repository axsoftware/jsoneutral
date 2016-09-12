
package com.jsoneutral.file.writer;

import com.jsoneutral.file.JsonMetadata;

public interface FileJsonWriterProcessor {

	String process(Object pojo, JsonMetadata metadata, String originalValue, String portionValue, String generatedValue);

}
