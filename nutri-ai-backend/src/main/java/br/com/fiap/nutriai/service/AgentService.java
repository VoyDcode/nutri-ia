package br.com.fiap.nutriai.service;

import br.com.fiap.nutriai.tools.NutritionTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class AgentService {

    private final ChatClient chatClient;
    private final NutritionTools nutritionTools;
    private final Map<String, InMemoryChatMemory> sessions = new ConcurrentHashMap<>();

    public AgentService(ChatClient.Builder builder, NutritionTools nutritionTools) {
        this.nutritionTools = nutritionTools;
        this.chatClient = builder
                .defaultSystem("""
                        Você é o NutriAgent, um agente nutricional autônomo inteligente.
                        Você possui memória da conversa e ferramentas para calcular IMC,
                        necessidade calórica diária, proteína e informações de alimentos.

                        Seu objetivo é criar planos alimentares personalizados ao longo da conversa.
                        Primeiro colete dados do usuário (nome, idade, peso, altura, sexo, nível de atividade
                        e objetivo: emagrecer, manter peso ou ganhar massa). Use as ferramentas para calcular
                        as métricas necessárias e então monte um plano alimentar personalizado.

                        Lembre-se de tudo que o usuário disse anteriormente na conversa.
                        Responda sempre em português brasileiro de forma amigável, motivadora e profissional.
                        """)
                .build();
    }

    public String chat(String sessionId, String message) {
        InMemoryChatMemory memory = sessions.computeIfAbsent(sessionId, k -> new InMemoryChatMemory());
        return chatClient.prompt()
                .user(message)
                .advisors(new MessageChatMemoryAdvisor(memory))
                .tools(nutritionTools)
                .call()
                .content();
    }

    public void clearSession(String sessionId) {
        sessions.remove(sessionId);
    }
}
