package com.axsoftware.test.write;

public class BoardHeader {
    private String date;
    private String message;
    private String code;

    public final static String ID = "HEADER";

    public String getDate() {
        return this.date;
    }

    public void setDate(final String date) {
        this.date = date;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }
}
