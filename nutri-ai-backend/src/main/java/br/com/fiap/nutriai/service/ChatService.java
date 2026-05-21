package br.com.fiap.nutriai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private final ChatClient chatClient;

    public ChatService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                        Você é NutriAI, um assistente nutricional especializado e empático.
                        Responda sempre em português brasileiro de forma clara, educativa e acessível.
                        Forneça informações sobre nutrição, dieta, alimentos e saúde alimentar.
                        Ao final de respostas sobre saúde, lembre o usuário de consultar um nutricionista.
                        """)
                .build();
    }

    public String chat(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .content();
    }
}
