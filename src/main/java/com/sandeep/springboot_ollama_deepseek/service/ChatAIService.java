package com.sandeep.springboot_ollama_deepseek.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ChatAIService {

    private final ChatClient chatClient;

    public ChatAIService(ChatClient.Builder chatClient){
        this.chatClient = chatClient.build();
    }

    public Flux<String> askToDeepSeekAI(String ask){
        return chatClient.prompt(ask).stream().content();
    }

}
