package com.axsoftware.jsoneutral.file;

/**
 * Custom field separator
 */

public class FieldSeparator {
    private final String tag;

    public FieldSeparator(final String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return this.tag;
    }

    @Override
    public String toString() {
        return this.getTag();
    }
}
