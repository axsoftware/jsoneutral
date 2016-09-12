
package com.jsoneutral.file.writer;

import static com.jsoneutral.file.util.StringDataUtil.formatDate;
import static com.jsoneutral.file.util.StringDataUtil.leftPad;
import static com.jsoneutral.file.util.StringDataUtil.removeDiacritics;
import static com.jsoneutral.file.util.StringDataUtil.rightPad;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import com.jsoneutral.file.AbstractFileProcessor;
import com.jsoneutral.file.DataRecord;
import com.jsoneutral.file.JsonMetadata;
import com.jsoneutral.file.exception.FileJsonException;
import com.jsoneutral.file.exception.FileJsonException.Type;
import com.jsoneutral.file.util.ReflectionUtil;


public class FileJsonWriterImpl extends AbstractFileProcessor implements FileJsonWriter {

	private static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");
	private static final String DATE_FORMAT = "yyyyMMddHHmmssSSS";

	@Override
	public Collection<String> execute(Collection<?> list, String configFile, String id) {
		return execute(list, configFile, id, new FileJsonWriterProcessor() {

			@Override
			public String process(Object pojo, JsonMetadata metadata, String originalValue, String portionValue, String generatedValue) {
				return generatedValue;
			}

		});
	}

	@Override
	public String execute(Object data, String configFile, String id) {
		Collection<String> lines = execute(Arrays.asList(data), configFile, id);
		return (lines.isEmpty() ? null : lines.iterator().next());
	}

	@Override
	public Collection<String> execute(Collection<?> list, String configFile, String id, FileJsonWriterProcessor processor) {
		return generateContent(getLines(list, configFile, id, processor));
	}

	@Override
	public String execute(Object data, String configFilePath, String configId, FileJsonWriterProcessor processor) {
		Collection<String> lines = generateContent(getLines(Arrays.asList(data), configFilePath, configId, processor));
		return (lines.isEmpty() ? null : lines.iterator().next());
	}

	@Override
	public File execute(Collection<?> list, String configFilePath, String configId, String fileName) {
		return execute(list, configFilePath, configId, null, fileName);
	}

	@Override
	public File execute(Collection<?> list, String configFile, String configId, FileJsonWriterProcessor processor, String filename) {
		try {
			return generateFile(execute(list, configFile, configId, processor), filename);
		} catch (Exception e) {
			throw new FileJsonException(Type.ERROR_GENERATE_FILE);
		}
	}

	private Collection<Map<JsonMetadata, String>> getLines(Collection<?> pojos, String configFilePath, String configId, FileJsonWriterProcessor processor) {
		Collection<Map<JsonMetadata, String>> lines = new ArrayList<>();
		Map<String, DataRecord> metadata = getMetadataFromJsonConfig(configFilePath);
		DataRecord lineMetadata = metadata.get(configId);

		for (Object pojo : pojos) {
			Map<JsonMetadata, String> lineValues = new TreeMap<>();

			for (JsonMetadata fragment : lineMetadata.getFragmentsMetadata()) {
				fragment.setMetadata(lineMetadata);
				lineValues.put(fragment, getFragmentValue(pojo, fragment, processor));
			}

			lines.add(lineValues);
		}

		return lines;
	}

	private String getFragmentValue(Object data, JsonMetadata fragment, FileJsonWriterProcessor processor) {
		
		Integer lengthFragment = fragment.getEnd() - fragment.getStart() + 1;
		
		String originalValue = getFieldValueAsString(data, fragment.getKey());
		
		if(originalValue.isEmpty()){
			return "";
		}
		
		Integer lengthOriginalValue = originalValue.length();
		
		Integer length = lengthOriginalValue < lengthFragment ? lengthOriginalValue: lengthFragment;   
		
		String portionValue = originalValue.substring(0, length);
		String generatedValue = rightPad(portionValue, lengthFragment, " ");

		if (processor != null) {
			generatedValue = processor.process(data, fragment, originalValue, portionValue, generatedValue);
			generatedValue = generatedValue.substring(0, lengthFragment);
			generatedValue = rightPad(generatedValue, lengthFragment, " ");
			generatedValue = removeDiacritics(generatedValue);
		}

		return generatedValue;
	}

	private String getFieldValueAsString(Object pojo, String property) {
		
		Field field  = ReflectionUtil.findField(pojo.getClass(), property);
		Object value = ReflectionUtil.readField(field, pojo, true);
		
		return value == null ? "" : value.toString();

	}

	private Collection<String> generateContent(Collection<Map<JsonMetadata, String>> lineValues) {
		Collection<String> lines = new ArrayList<>();

		for (Map<JsonMetadata, String> lineValue : lineValues) {
			StringBuilder line = new StringBuilder();

			for (JsonMetadata metadata : lineValue.keySet()) {
				String value = lineValue.get(metadata);
				line.append(leftPad(value, metadata.getEnd() - line.length(), " "));
			}

			String lineContent = line.toString();
			Integer expectedLength = lineValue.keySet().iterator().next().getMetadata().getLengthForWrite();

			if (expectedLength != null) {
				if (lineContent.length() > expectedLength) {
					lineContent = lineContent.substring(0, expectedLength);
				} else {
					lineContent = rightPad(lineContent, expectedLength);
				}
			}

			lines.add(lineContent);
		}

		return lines;
	}

	@Override
	public File generateFile(Collection<String> lines, String filename) throws Exception {
		
		final Path tmpFile = Files.createTempFile(randomFileName(), ".txt");
		
		Files.write(tmpFile, lines, DEFAULT_CHARSET);

		return tmpFile.toFile();
	}

	public static String randomFileName() throws Exception {
		return String.format("%s.%s", formatDate(new Date(), DATE_FORMAT), UUID.randomUUID().toString().substring(0, 10));
	}
	
}