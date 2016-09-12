
package com.axsoftware.jsoneutral.file.writer;

import com.axsoftware.jsoneutral.file.JsonMetadata;

public interface FileJsonWriterProcessor {

	String process(Object pojo, JsonMetadata metadata, String originalValue, String portionValue, String generatedValue);

}
