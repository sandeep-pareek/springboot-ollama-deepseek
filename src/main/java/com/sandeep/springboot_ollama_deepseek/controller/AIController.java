package com.sandeep.springboot_ollama_deepseek.controller;


import com.sandeep.springboot_ollama_deepseek.service.ChatAIService;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.Map;

@RestController
@RequestMapping("/deepSeek")
public class AIController {

    private final OllamaChatModel chatModel;



    @Autowired
    public AIController(OllamaChatModel chatModel) {
        this.chatModel = chatModel;
    }

    @GetMapping("/testMe")
    public String getResponse(){
        return "Hello, AI world of infinite possibilities";
    }

    @GetMapping("/generate")
    public Map<String,String> generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", this.chatModel.call(message));
    }

    @GetMapping("/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return this.chatModel.stream(prompt);
    }

}
