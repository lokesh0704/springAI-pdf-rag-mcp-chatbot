package com.lokesh.rag_ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class ChatController {

    // ChatClient used to send prompts to the LLM
    private final ChatClient chatClient;

    // Constructor Injection
    // Spring automatically injects:
    // 1. ChatClient.Builder
    // 2. VectorStore bean
    public ChatController(ChatClient.Builder builder,
                          VectorStore vectorStore) {

        // Build ChatClient with RAG support
        this.chatClient = builder

                // Register QuestionAnswerAdvisor
                // This advisor automatically: - Takes user question , Searches Vector DB, Retrieves relevant chunks, Adds them to prompt context
                .defaultAdvisors(
                        QuestionAnswerAdvisor.builder(vectorStore)
                                .build()
                )
                // Create ChatClient instance
                .build();
    }
    
    // API Endpoint
    // URL: http://localhost:8080/
    @GetMapping("/")
    public String chat() {

        // Create a prompt

        return chatClient.prompt()

                // User question
                .user("Please tell me about Lokesh's skillset and projects")

                // Call LLM
                .call()

                // Extract plain text response
                .content();
    }
}