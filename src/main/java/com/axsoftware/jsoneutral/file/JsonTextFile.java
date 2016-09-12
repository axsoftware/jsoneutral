package com.axsoftware.jsoneutral.file;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

public class JsonTextFile implements Iterator<JsonDataLine> {

	private BufferedReader bufferedReader;
	private Map<String, DataRecord> linesMetadata;

	private Integer currentIndex;
	private JsonDataLine currentStructuredLine;

	private String nextStringLine;

	public JsonTextFile(BufferedReader bufferedReader, Map<String, DataRecord> linesMetadata) {
		this.bufferedReader = bufferedReader;
		this.linesMetadata = linesMetadata;
		currentIndex = 0;
		try {
			nextStringLine = bufferedReader.readLine();
		} catch (IOException e) {
		}
	}

	@Override
	public boolean hasNext() {
		return nextStringLine != null;
	}

	public Integer getIndex() {
		return currentIndex;
	}

	@Override
	public JsonDataLine next() {

		currentIndex++;

		try {
			if (nextStringLine != null) {
				currentStructuredLine = new JsonDataLine(linesMetadata, nextStringLine);
				nextStringLine = bufferedReader.readLine();

				return currentStructuredLine;
			}
		} catch (IOException e) {
		}
		return null;
	}

	@Override
	public void remove() {
	}
}