package com.triangle.models;

public class TestRequest {
    private String separator;
    private String input;


    public TestRequest(String separator, Triangle triangle) {
        this.separator = separator;
        this.input = String.format("%s%s%s%s%s", triangle.getaSide(), separator, triangle.getbSIde(), separator, triangle.getcSide());
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }
}


