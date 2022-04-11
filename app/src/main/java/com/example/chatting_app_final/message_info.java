package com.example.chatting_app_final;

public class message_info {

    private String text;
    private String user;

    public message_info() {
    }

    public message_info(String text, String user) {
        this.text = text;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
