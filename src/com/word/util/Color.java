package com.word.util;

enum Color {
    GREEN("[32m");

    private final String fontCode;

    Color(String fontCode) {
        this.fontCode = fontCode;
    }

    public String setFontColor(String text) {
        return String.format("\033%s%s\033[0m", fontCode, text);
    }
}