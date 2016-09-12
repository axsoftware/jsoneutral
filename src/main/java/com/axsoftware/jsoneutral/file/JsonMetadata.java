package com.axsoftware.jsoneutral.file;

public class JsonMetadata implements Comparable<JsonMetadata> {

	private Integer start;
	private Integer end;
	private String key;
	private DataRecord metadata;

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getEnd() {
		return end;
	}

	public void setEnd(Integer end) {
		this.end = end;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public DataRecord getMetadata() {
		return metadata;
	}

	public void setMetadata(DataRecord metadata) {
		this.metadata = metadata;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		JsonMetadata other = (JsonMetadata) obj;
		if (key == null) {
			if (other.key != null) {
				return false;
			}
		} else if (!key.equals(other.key)) {
			return false;
		}
		return true;
	}

	@Override
	public int compareTo(JsonMetadata o) {
		return getStart().compareTo(o.getStart());
	}

	public Integer length() {
		return getEnd() - getStart() + 1;
	}

}