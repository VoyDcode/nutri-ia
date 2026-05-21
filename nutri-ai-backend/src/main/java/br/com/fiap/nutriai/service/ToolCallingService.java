package br.com.fiap.nutriai.service;

import br.com.fiap.nutriai.tools.NutritionTools;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class ToolCallingService {

    private final ChatClient chatClient;
    private final NutritionTools nutritionTools;

    public ToolCallingService(ChatClient.Builder builder, NutritionTools nutritionTools) {
        this.nutritionTools = nutritionTools;
        this.chatClient = builder
                .defaultSystem("""
                        Você é um assistente nutricional com acesso a ferramentas de cálculo.
                        Quando o usuário fornecer dados pessoais (peso, altura, idade, sexo, atividade física),
                        use as ferramentas disponíveis para calcular IMC, necessidade calórica e proteína.
                        Interprete os resultados e forneça recomendações práticas em português.
                        """)
                .build();
    }

    public String calculate(String request) {
        return chatClient.prompt()
                .user(request)
                .tools(nutritionTools)
                .call()
                .content();
    }
}
