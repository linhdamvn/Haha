package com.example.thanhdam.myapplication;

public class StoryModel {
    public String name;
    public String image;
    public String content;
    public String audio;

    public StoryModel(String name, String image, String content, String audio) { // alt + inset => ham khoi tao
        this.name = name;
        this.image = image;
        this.content = content;
        this.audio = audio;
    }
}
