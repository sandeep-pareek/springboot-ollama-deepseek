package com.sandeep.springboot.deepseek.model;

import java.util.List;



public class ModelData {

    private String model;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<CustomMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<CustomMessage> messages) {
        this.messages = messages;
    }

    private List<CustomMessage> messages;


}
