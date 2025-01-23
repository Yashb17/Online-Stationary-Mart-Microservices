package com.onlinestationarymart.promptAI_service.controller;

import com.onlinestationarymart.promptAI_service.service.PromptAIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/prompt/")
public class PromptAIController {

    @Autowired
    PromptAIService promptAIService;

    private static final Logger LOGGER = LoggerFactory.getLogger(PromptAIController.class);

    @GetMapping("generate/joke")
    public String generateJoke(@RequestParam String topic){
        LOGGER.info("Received generate joke request for topic: {}!", topic);
        return promptAIService.generateJokeForTopic(topic);
    }

    @GetMapping("generate/fact")
    public String generateFact(@RequestParam String topic){
        LOGGER.info("Received generate fact request for topic: {}!", topic);
        return promptAIService.generateFactForTopic(topic);
    }
}
