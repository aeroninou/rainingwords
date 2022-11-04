package com.word.util;

enum Color {
    GREEN("[32m", "[42m");

    private final String fontCode;
    private final String backgroundCode;

    Color(String fontCode, String backgroundCode) {
        this.fontCode = fontCode;
        this.backgroundCode = backgroundCode;
    }

    public String setFontColor(String text) {
        return String.format("\033%s%s\033[0m", fontCode, text);
    }

    public String setBackgroundColor(String text) {
        return String.format("\033%s%s\033[0m", backgroundCode, text);
    }
}