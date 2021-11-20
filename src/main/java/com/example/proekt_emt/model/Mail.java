package com.example.proekt_emt.model;

public class Mail {

    private String username;
    private String text;
    private String subject;

    public Mail(String username, String subject, String text) {
        this.username = username;
        this.text = text;
        this.subject = subject;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
