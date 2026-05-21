package br.com.fiap.nutriai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.QuestionAnswerAdvisor;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

@Service
public class RagService {

    private final ChatClient chatClient;

    public RagService(ChatClient.Builder builder, VectorStore vectorStore) {
        this.chatClient = builder
                .defaultSystem("""
                        Você é um especialista em nutrição e dietética.
                        Use exclusivamente o contexto fornecido para responder perguntas.
                        Se a informação não estiver no contexto, informe ao usuário que não encontrou
                        a informação na base de conhecimento e sugira consultar um nutricionista.
                        Responda sempre em português brasileiro de forma clara e didática.
                        """)
                .defaultAdvisors(new QuestionAnswerAdvisor(vectorStore))
                .build();
    }

    public String query(String question) {
        return chatClient.prompt()
                .user(question)
                .call()
                .content();
    }
}
