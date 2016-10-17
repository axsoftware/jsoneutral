package com.axsoftware.test.write;

public class BoardContent {
    private String code;
    private String message;
    private String register;

    public final static String ID = "CONTENT";

    public String getCode() {
        return this.code;
    }

    public void setCode(final String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }

    public String getRegister() {
        return this.register;
    }

    public void setRegister(final String register) {
        this.register = register;
    }
}
