package br.com.fiap.nutriai.service;

import br.com.fiap.nutriai.dto.NutritionAnalysis;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class StructuredOutputService {

    private final ChatClient chatClient;

    public StructuredOutputService(ChatClient.Builder builder) {
        this.chatClient = builder
                .defaultSystem("""
                        Você é um analista nutricional preciso. Analise refeições descritas pelo usuário
                        e retorne sempre uma análise nutricional estruturada e completa.
                        Estime valores nutricionais com base em porções típicas quando não especificado.
                        Para nutritionRating use: Excelente, Bom, Regular ou Ruim.
                        Forneça de 2 a 4 recomendações práticas.
                        """)
                .build();
    }

    public NutritionAnalysis analyze(String mealDescription) {
        return chatClient.prompt()
                .user("Analise a seguinte refeição e forneça a análise nutricional completa: " + mealDescription)
                .call()
                .entity(NutritionAnalysis.class);
    }
}
