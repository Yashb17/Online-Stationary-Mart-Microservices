package com.onlinestationarymart.promptAI_service.service;

import com.onlinestationarymart.promptAI_service.constants.Prompts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromptAIService {

    @Autowired
    OllamaChatModel chatModel;

    private static final Logger LOGGER = LoggerFactory.getLogger(PromptAIService.class);

    public String generateJokeForTopic(String topic){
        LOGGER.info("Inside PromptAIService: Entering Method generateJokeForTopic!");
        ChatResponse chatResponse = chatModel.call(
                new Prompt(String.format(Prompts.generateJokePrompt, topic),
                OllamaOptions.create().withModel("llama3")));
        LOGGER.info("Exiting PromptAIService: Returning Response!");
        return chatResponse.getResult().getOutput().getContent();
    }

    public String generateFactForTopic(String topic) {
        LOGGER.info("Inside PromptAIService: Entering Method generateFactForTopic!");
        ChatResponse chatResponse = chatModel.call(
                new Prompt(String.format(Prompts.generateFactPrompt, topic),
                        OllamaOptions.create().withModel("llama3")));
        LOGGER.info("Exiting PromptAIService: Returning Response!");
        return chatResponse.getResult().getOutput().getContent();
    }
}
