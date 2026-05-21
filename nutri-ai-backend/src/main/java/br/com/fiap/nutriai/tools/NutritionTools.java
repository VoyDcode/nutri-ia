package br.com.fiap.nutriai.tools;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
public class NutritionTools {

    @Tool(description = "Calcula o IMC (Índice de Massa Corporal) dado peso em kg e altura em metros. Retorna o valor do IMC e a classificação.")
    public String calculateBMI(double weightKg, double heightM) {
        double bmi = weightKg / (heightM * heightM);
        String classification;
        if (bmi < 18.5) classification = "Abaixo do peso";
        else if (bmi < 25.0) classification = "Peso normal";
        else if (bmi < 30.0) classification = "Sobrepeso";
        else if (bmi < 35.0) classification = "Obesidade Grau I";
        else if (bmi < 40.0) classification = "Obesidade Grau II";
        else classification = "Obesidade Grau III (Mórbida)";
        return String.format("IMC: %.2f — Classificação: %s", bmi, classification);
    }

    @Tool(description = "Calcula a necessidade calórica diária (TDEE) com base em idade, peso (kg), altura (cm), sexo (masculino/feminino) e nível de atividade (sedentario, leve, moderado, intenso, muito_intenso).")
    public String calculateDailyCalories(int age, double weightKg, double heightCm, String sex, String activityLevel) {
        double bmr;
        if (sex.equalsIgnoreCase("masculino") || sex.equalsIgnoreCase("male")) {
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age + 5;
        } else {
            bmr = 10 * weightKg + 6.25 * heightCm - 5 * age - 161;
        }
        double factor = switch (activityLevel.toLowerCase()) {
            case "sedentario", "sedentary" -> 1.2;
            case "leve", "light" -> 1.375;
            case "moderado", "moderate" -> 1.55;
            case "intenso", "active" -> 1.725;
            case "muito_intenso", "very_active" -> 1.9;
            default -> 1.55;
        };
        int tdee = (int) (bmr * factor);
        int weightLoss = tdee - 400;
        int weightGain = tdee + 300;
        return String.format(
                "Metabolismo basal (BMR): %.0f kcal | Gasto calórico total (TDEE): %d kcal | Para emagrecer: ~%d kcal/dia | Para ganhar massa: ~%d kcal/dia",
                bmr, tdee, weightLoss, weightGain
        );
    }

    @Tool(description = "Retorna informações nutricionais de alimentos comuns brasileiros. Informe o nome do alimento em português.")
    public String getFoodNutrition(String food) {
        return switch (food.toLowerCase().trim()) {
            case "arroz", "arroz branco" ->
                    "Arroz branco cozido (100g): 130 kcal | Carboidratos: 28g | Proteína: 2.7g | Gordura: 0.3g | Fibra: 0.4g";
            case "feijão", "feijao", "feijão cozido" ->
                    "Feijão cozido (100g): 76 kcal | Carboidratos: 13.6g | Proteína: 4.8g | Gordura: 0.5g | Fibra: 8.5g";
            case "frango", "peito de frango", "frango grelhado" ->
                    "Peito de frango grelhado (100g): 165 kcal | Proteína: 31g | Gordura: 3.6g | Carboidratos: 0g";
            case "ovo", "ovo cozido" ->
                    "Ovo cozido (1 unidade ~50g): 78 kcal | Proteína: 6g | Gordura: 5g | Carboidratos: 0.6g";
            case "banana" ->
                    "Banana prata (1 média ~100g): 89 kcal | Carboidratos: 23g | Proteína: 1.1g | Gordura: 0.3g | Fibra: 2.6g";
            case "batata doce" ->
                    "Batata doce cozida (100g): 86 kcal | Carboidratos: 20g | Proteína: 1.6g | Gordura: 0.1g | Fibra: 3g";
            case "aveia" ->
                    "Aveia em flocos (100g): 389 kcal | Carboidratos: 66g | Proteína: 17g | Gordura: 7g | Fibra: 11g";
            case "salmão", "salmao" ->
                    "Salmão grelhado (100g): 208 kcal | Proteína: 20g | Gordura: 13g | Carboidratos: 0g | Ômega-3: 2.3g";
            case "abacate" ->
                    "Abacate (100g): 160 kcal | Carboidratos: 9g | Proteína: 2g | Gordura: 15g | Fibra: 6.7g";
            case "whey", "whey protein" ->
                    "Whey protein (1 scoop ~30g): 120 kcal | Proteína: 24g | Carboidratos: 3g | Gordura: 2g";
            case "leite", "leite integral" ->
                    "Leite integral (200ml): 122 kcal | Proteína: 6.4g | Carboidratos: 9.4g | Gordura: 6.4g | Cálcio: 240mg";
            case "iogurte", "iogurte natural" ->
                    "Iogurte natural integral (170g): 100 kcal | Proteína: 9g | Carboidratos: 7g | Gordura: 3.5g";
            default ->
                    String.format("Alimento '%s' não encontrado na base local. Consulte uma tabela nutricional completa como a Tabela TACO (UNICAMP).", food);
        };
    }

    @Tool(description = "Calcula a quantidade de proteína diária recomendada em gramas com base no peso corporal (kg) e objetivo (manutencao, emagrecimento, hipertrofia, esporte).")
    public String calculateProteinNeeds(double weightKg, String objective) {
        double minFactor;
        double maxFactor;
        String advice;
        switch (objective.toLowerCase()) {
            case "emagrecimento" -> {
                minFactor = 1.6;
                maxFactor = 2.2;
                advice = "Alta ingestão de proteína preserva massa muscular durante o déficit calórico.";
            }
            case "hipertrofia" -> {
                minFactor = 1.6;
                maxFactor = 2.2;
                advice = "Distribua em 4-6 refeições ao longo do dia para maximizar a síntese proteica.";
            }
            case "esporte", "atletismo" -> {
                minFactor = 1.4;
                maxFactor = 1.7;
                advice = "Inclua proteínas nas refeições pré e pós-treino.";
            }
            default -> {
                minFactor = 0.8;
                maxFactor = 1.2;
                advice = "Distribua em 3-4 refeições diárias.";
            }
        }
        int min = (int) (weightKg * minFactor);
        int max = (int) (weightKg * maxFactor);
        return String.format("Proteína recomendada para %s: %dg a %dg por dia. %s", objective, min, max, advice);
    }
}
