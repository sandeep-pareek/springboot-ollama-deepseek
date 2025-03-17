package com.sandeep.springboot.deepseek.controller;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sandeep.springboot.deepseek.model.CustomMessage;
import com.sandeep.springboot.deepseek.model.ModelData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

@RestController
@PropertySource("classpath:custom-app.properties")
@RequestMapping("/deepSeek")
@Slf4j
public class AIController {


    private final OllamaChatModel chatModel;

    @Value("${openroute.api.key}")
    private String apiKey;

    @Value("${openrouter.url}")
    private String openrouterUrl;

    @Value("${ai.model}")
    private String aiModel;

    @Value("${ai.role}")
    private String aiRole;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

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

    @GetMapping("/v2/generateStream")
    public String generateStreamV2(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message){
        log.info("Asking deepseek : {}", message);
        HttpHeaders headers = new HttpHeaders();


        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);
        ModelData modelData = new ModelData();
        CustomMessage customMessage = new CustomMessage();
        customMessage.setRole(aiRole);
        customMessage.setContent(message);
        modelData.setMessages(List.of(customMessage));
        modelData.setModel(aiModel);


        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        HttpEntity<ModelData> entity = new HttpEntity<>(modelData, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                openrouterUrl, HttpMethod.POST, entity, String.class);

        log.info("Returning the answer");
        return response.getBody();
    }

}
