package com.example.physactivtongtwist;

public class TextBlock {
    private String text;
    private int id;

    public TextBlock(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }
}