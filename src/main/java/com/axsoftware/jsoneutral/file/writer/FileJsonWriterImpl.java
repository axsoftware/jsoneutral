
package com.axsoftware.jsoneutral.file.writer;

import com.axsoftware.jsoneutral.file.AbstractFileProcessor;
import com.axsoftware.jsoneutral.file.DataRecord;
import com.axsoftware.jsoneutral.file.FieldSeparator;
import com.axsoftware.jsoneutral.file.JsonMetadata;
import com.axsoftware.jsoneutral.file.exception.FileJsonException;
import com.axsoftware.jsoneutral.file.exception.FileJsonException.Type;
import com.axsoftware.jsoneutral.file.util.ReflectionUtil;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static com.axsoftware.jsoneutral.file.util.ConditionalUtil.coalesce;
import static com.axsoftware.jsoneutral.file.util.StringDataUtil.*;

public class FileJsonWriterImpl extends AbstractFileProcessor implements FileJsonWriter {

    private static final String DEFAULT_EXTENSION = ".txt";
    private static final String DEFAULT_CHARSET = "UTF-8";
    private static final String DATE_FORMAT = "yyyyMMddHHmmssSSS";
    private FieldSeparator fieldSeparator = new FieldSeparator("");

    @Override
    public Collection<String> execute(final Collection<?> list, final String configFile, final String id) {
        return execute(list, configFile, id, (FileJsonWriterProcessor) (pojo, metadata, originalValue, portionValue, generatedValue) -> generatedValue);
    }

    @Override
    public String execute(final Object data, final String configFile, final String id) {
        final Collection<String> lines = execute(Arrays.asList(data), configFile, id);
        return lines.isEmpty() ? null : lines.iterator().next();
    }

    @Override
    public Collection<String> execute(final Collection<?> list, final String configFile, final String id, final FileJsonWriterProcessor processor) {
        return generateContent(getLines(list, configFile, id, processor));
    }

    @Override
    public String execute(final Object data, final String configFilePath, final String configId, final FileJsonWriterProcessor processor) {
        final Collection<String> lines = generateContent(getLines(Arrays.asList(data), configFilePath, configId, processor));
        return lines.isEmpty() ? null : lines.iterator().next();
    }

    @Override
    public File execute(final Collection<?> list, final String configFilePath, final String configId, final String fileName) {
        return execute(list, configFilePath, configId, null, fileName);
    }

    @Override
    public File execute(final Collection<?> list, final String configFile, final String configId, final FileJsonWriterProcessor processor, final String filename) {
        try {
            return generateFile(execute(list, configFile, configId, processor), filename);
        } catch (final Exception e) {
            throw new FileJsonException(Type.ERROR_GENERATE_FILE);
        }
    }

    private Collection<Map<JsonMetadata, String>> getLines(final Collection<?> pojos, final String configFilePath, final String configId, final FileJsonWriterProcessor processor) {
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

    private String getFragmentValue(final Object data, final JsonMetadata fragment, final FileJsonWriterProcessor processor) {

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

    private String getFieldValueAsString(final Object pojo, final String property) {

        final Field field = ReflectionUtil.findField(pojo.getClass(), property);
        final Object value = ReflectionUtil.readField(field, pojo, true);

        return value == null ? "" : value.toString();

    }

    private Collection<String> generateContent(final Collection<Map<JsonMetadata, String>> lineValues) {
        final Collection<String> lines = new ArrayList<>();

        for (final Map<JsonMetadata, String> lineValue : lineValues) {
            final StringBuilder line = new StringBuilder();

            for (final JsonMetadata metadata : lineValue.keySet()) {
                final String value = lineValue.get(metadata);
                if (!this.fieldSeparator.toString().isEmpty()) {
                    line.append(value + this.fieldSeparator);
                } else {
                    line.append(leftPad(value, metadata.getEnd() - line.length(), " "));
                }

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
    public File generateFile(final Collection<String> lines, final String filename) throws Exception {
        return generateFile(lines, filename, null, null, null);
    }

    @Override
    public File generateFile(final Collection<String> lines, final String filename, final String extension) throws Exception {
        return generateFile(lines, filename, null, null, null);
    }

    @Override
    public File generateFile(final Collection<String> lines, final String filename, final String extension, final String encoding, final Path dir) throws Exception {
        final Path file;

        if (null == dir) {
            file = Files.createTempFile(coalesce(filename, randomFileName()), coalesce(extension, DEFAULT_EXTENSION));
        } else {
            file = Files.createTempFile(dir, coalesce(filename, randomFileName()), coalesce(extension, DEFAULT_EXTENSION));
        }

        Files.write(file, lines, Charset.forName(coalesce(encoding, DEFAULT_CHARSET)));

        return file.toFile();
    }

    @Override
    public void setFieldSeparator(final FieldSeparator fieldSeparator) {
        this.fieldSeparator = fieldSeparator;
    }

    private static String randomFileName() {
        return String.format("%s.%s", formatDate(new Date(), DATE_FORMAT), UUID.randomUUID().toString().substring(0, 10));
    }
}
