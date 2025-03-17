package com.sandeep.springboot.deepseek.model;

public class CustomMessage {

    private String role;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String content;


}
