package com.sandeep.springboot_ollama_deepseek.controller;

import com.sandeep.springboot_ollama_deepseek.service.ChatAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/reactive")
public class ReactiveAIController {

    private final ChatAIService service;

    @Autowired
    public ReactiveAIController(ChatAIService service){
        this.service = service;
    }


    @GetMapping("/generateStream")
    public Flux<String> generateStreamFlux(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message){
        return service.askToDeepSeekAI(message);
    }

}
