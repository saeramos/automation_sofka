package com.automation.models;

public class ContactUsData {
    private String name;
    private String email;
    private String subject;
    private String message;
    private String filePath;

    public ContactUsData(String name, String email, String subject, String message, String filePath) {
        this.name = name;
        this.email = email;
        this.subject = subject;
        this.message = message;
        this.filePath = filePath;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getSubject() {
        return subject;
    }

    public String getMessage() {
        return message;
    }

    public String getFilePath() {
        return filePath;
    }
}

