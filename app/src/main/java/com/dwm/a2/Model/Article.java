package com.dwm.a2.Model;

public class Article {
    private String topic;
    private String title;
    private String content;

    public Article(){}

    public Article(String topic, String title, String content){
        this.topic = topic;
        this.title = title;
        this.content = content;
    }
    
    public String getTopic() {
        return topic;
    }
    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
