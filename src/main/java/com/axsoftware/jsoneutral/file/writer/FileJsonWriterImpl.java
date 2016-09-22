
package com.axsoftware.jsoneutral.file.writer;

import static com.axsoftware.jsoneutral.file.util.ConditionalUtil.coalesce;
import static com.axsoftware.jsoneutral.file.util.StringDataUtil.formatDate;
import static com.axsoftware.jsoneutral.file.util.StringDataUtil.leftPad;
import static com.axsoftware.jsoneutral.file.util.StringDataUtil.removeDiacritics;
import static com.axsoftware.jsoneutral.file.util.StringDataUtil.rightPad;

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

import com.axsoftware.jsoneutral.file.AbstractFileProcessor;
import com.axsoftware.jsoneutral.file.DataRecord;
import com.axsoftware.jsoneutral.file.JsonMetadata;
import com.axsoftware.jsoneutral.file.exception.FileJsonException;
import com.axsoftware.jsoneutral.file.exception.FileJsonException.Type;
import com.axsoftware.jsoneutral.file.util.ReflectionUtil;

public class FileJsonWriterImpl extends AbstractFileProcessor implements FileJsonWriter {

	private static final String DEFAULT_EXTENSION = ".txt";
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String DATE_FORMAT = "yyyyMMddHHmmssSSS";

	@Override
	public Collection<String> execute(Collection<?> list, String configFile, String id) {
		return execute(list, configFile, id, (FileJsonWriterProcessor) (pojo, metadata, originalValue, portionValue, generatedValue) -> generatedValue);
	}

	@Override
	public String execute(Object data, String configFile, String id) {
		final Collection<String> lines = execute(Arrays.asList(data), configFile, id);
		return lines.isEmpty() ? null : lines.iterator().next();
	}

	@Override
	public Collection<String> execute(Collection<?> list, String configFile, String id, FileJsonWriterProcessor processor) {
		return generateContent(getLines(list, configFile, id, processor));
	}

	@Override
	public String execute(Object data, String configFilePath, String configId, FileJsonWriterProcessor processor) {
		final Collection<String> lines = generateContent(getLines(Arrays.asList(data), configFilePath, configId, processor));
		return lines.isEmpty() ? null : lines.iterator().next();
	}

	@Override
	public File execute(Collection<?> list, String configFilePath, String configId, String fileName) {
		return execute(list, configFilePath, configId, null, fileName);
	}

	@Override
	public File execute(Collection<?> list, String configFile, String configId, FileJsonWriterProcessor processor, String filename) {
		try {
			return generateFile(execute(list, configFile, configId, processor), filename);
		} catch (final Exception e) {
			throw new FileJsonException(Type.ERROR_GENERATE_FILE);
		}
	}

	private Collection<Map<JsonMetadata, String>> getLines(Collection<?> pojos, String configFilePath, String configId, FileJsonWriterProcessor processor) {
		final Collection<Map<JsonMetadata, String>> lines = new ArrayList<>();
		final Map<String, DataRecord> metadata = getMetadataFromJsonConfig(configFilePath);
		final DataRecord lineMetadata = metadata.get(configId);

		for (final Object pojo : pojos) {
			final Map<JsonMetadata, String> lineValues = new TreeMap<>();

			for (final JsonMetadata fragment : lineMetadata.getFragmentsMetadata()) {
				fragment.setMetadata(lineMetadata);
				lineValues.put(fragment, getFragmentValue(pojo, fragment, processor));
			}

			lines.add(lineValues);
		}

		return lines;
	}

	private String getFragmentValue(Object data, JsonMetadata fragment, FileJsonWriterProcessor processor) {

		final Integer lengthFragment = fragment.getEnd() - fragment.getStart() + 1;

		final String originalValue = getFieldValueAsString(data, fragment.getKey());

		if (originalValue.isEmpty())
			return "";

		final Integer lengthOriginalValue = originalValue.length();

		final Integer length = lengthOriginalValue < lengthFragment ? lengthOriginalValue : lengthFragment;

		final String portionValue = originalValue.substring(0, length);
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

		final Field field = ReflectionUtil.findField(pojo.getClass(), property);
		final Object value = ReflectionUtil.readField(field, pojo, true);

		return value == null ? "" : value.toString();

	}

	private Collection<String> generateContent(Collection<Map<JsonMetadata, String>> lineValues) {
		final Collection<String> lines = new ArrayList<>();

		for (final Map<JsonMetadata, String> lineValue : lineValues) {
			final StringBuilder line = new StringBuilder();

			for (final JsonMetadata metadata : lineValue.keySet()) {
				final String value = lineValue.get(metadata);
				line.append(leftPad(value, metadata.getEnd() - line.length(), " "));
			}

			String lineContent = line.toString();
			final Integer expectedLength = lineValue.keySet().iterator().next().getMetadata().getLengthForWrite();

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
		return generateFile(lines, filename, null, null, null);
	}

	@Override
	public File generateFile(Collection<String> lines, String filename, String extension) throws Exception {
		return generateFile(lines, filename, null, null, null);
	}

	@Override
	public File generateFile(Collection<String> lines, String filename, String extension, String encoding, Path dir) throws Exception {
		final Path file;

		if (null == dir) {
			file = Files.createTempFile(coalesce(filename, randomFileName()), coalesce(extension, DEFAULT_EXTENSION));
		} else {
			file = Files.createTempFile(dir, coalesce(filename, randomFileName()), coalesce(extension, DEFAULT_EXTENSION));
		}

		Files.write(file, lines, Charset.forName(coalesce(encoding, DEFAULT_CHARSET)));

		return file.toFile();
	}

	private static String randomFileName() {
		return String.format("%s.%s", formatDate(new Date(), DATE_FORMAT), UUID.randomUUID().toString().substring(0, 10));
	}
}