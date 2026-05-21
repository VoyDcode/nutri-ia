package br.com.fiap.nutriai.dto;

import java.util.List;

public record NutritionAnalysis(
        String mealDescription,
        List<FoodItem> foods,
        int totalCalories,
        double totalProteinGrams,
        double totalCarbsGrams,
        double totalFatGrams,
        String nutritionRating,
        List<String> recommendations
) {
    public record FoodItem(
            String name,
            String quantity,
            int calories,
            double proteinGrams,
            double carbsGrams,
            double fatGrams
    ) {
    }
}
