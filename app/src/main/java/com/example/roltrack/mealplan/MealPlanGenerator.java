package com.example.roltrack.mealplan;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MealPlanGenerator {

    public static MealPlan generate(double weight, double heightCm, int age, String goal) {
        double heightM = heightCm / 100.0;
        double bmi = weight / (heightM * heightM);

        String normalizedGoal = goal == null ? "" : goal.toLowerCase(Locale.ROOT);

        boolean wantsMuscle = normalizedGoal.contains("músculo")
                || normalizedGoal.contains("musculo")
                || normalizedGoal.contains("fuerza");

        boolean wantsWeightLoss = normalizedGoal.contains("bajar")
                || normalizedGoal.contains("peso")
                || normalizedGoal.contains("grasa");

        if (wantsWeightLoss || bmi >= 27) {
            return buildFatLossPlan(weight);
        }

        if (wantsMuscle) {
            return buildMusclePlan(weight);
        }

        return buildGeneralPlan(weight);
    }

    private static MealPlan buildFatLossPlan(double weight) {
        int protein = (int) Math.round(weight * 1.8);
        List<MealSuggestion> meals = new ArrayList<>();

        meals.add(new MealSuggestion(
                "Desayuno",
                "Avena con proteína",
                "• 60 g de avena\n" +
                        "• 1 scoop de proteína o 4 claras\n" +
                        "• 1 plátano\n" +
                        "• Café o té sin azúcar"
        ));

        meals.add(new MealSuggestion(
                "Colación 1",
                "Yogur con nueces",
                "• 1 yogur griego natural\n" +
                        "• 15 g de nueces o almendras"
        ));

        meals.add(new MealSuggestion(
                "Comida",
                "Pollo con arroz y verduras",
                "• 150-200 g de pechuga de pollo\n" +
                        "• 100 g de arroz cocido\n" +
                        "• Verduras al vapor o ensalada\n" +
                        "• 1 cucharadita de aceite de oliva"
        ));

        meals.add(new MealSuggestion(
                "Colación 2",
                "Snack ligero",
                "• 1 manzana\n" +
                        "• 1 lata de atún o 2 huevos cocidos"
        ));

        meals.add(new MealSuggestion(
                "Cena",
                "Cena ligera alta en proteína",
                "• 150 g de pescado, pollo o atún\n" +
                        "• Verduras salteadas\n" +
                        "• 1 tortilla o porción pequeña de arroz si entrenaste"
        ));

        return new MealPlan(
                "Plan alimenticio para bajar grasa",
                "1900 - 2200 kcal",
                protein + " g de proteína aprox.",
                "Plan enfocado en déficit moderado, saciedad y alta proteína para preservar masa muscular.",
                meals
        );
    }

    private static MealPlan buildMusclePlan(double weight) {
        int protein = (int) Math.round(weight * 2.0);
        List<MealSuggestion> meals = new ArrayList<>();

        meals.add(new MealSuggestion(
                "Desayuno",
                "Desayuno alto en energía",
                "• 4 huevos\n" +
                        "• 2-4 tortillas o pan integral\n" +
                        "• 1 fruta\n" +
                        "• Avena o yogur"
        ));

        meals.add(new MealSuggestion(
                "Colación 1",
                "Snack proteico",
                "• 1 yogur griego\n" +
                        "• 1 plátano\n" +
                        "• 1 cucharada de crema de cacahuate"
        ));

        meals.add(new MealSuggestion(
                "Comida",
                "Comida principal para volumen",
                "• 200 g de pollo, carne o pescado\n" +
                        "• 150-200 g de arroz, pasta o papa\n" +
                        "• Verduras\n" +
                        "• 1 aguacate pequeño o aceite de oliva"
        ));

        meals.add(new MealSuggestion(
                "Colación 2",
                "Pre o post entreno",
                "• Sándwich de pechuga de pavo\n" +
                        "• o licuado con avena, leche y proteína"
        ));

        meals.add(new MealSuggestion(
                "Cena",
                "Cena completa",
                "• 180-200 g de proteína\n" +
                        "• 100-150 g de carbohidrato\n" +
                        "• Verduras"
        ));

        return new MealPlan(
                "Plan alimenticio para ganar músculo",
                "2500 - 3000 kcal",
                protein + " g de proteína aprox.",
                "Plan orientado a superávit moderado, recuperación muscular y mejor rendimiento en entrenamiento.",
                meals
        );
    }

    private static MealPlan buildGeneralPlan(double weight) {
        int protein = (int) Math.round(weight * 1.6);
        List<MealSuggestion> meals = new ArrayList<>();

        meals.add(new MealSuggestion(
                "Desayuno",
                "Desayuno equilibrado",
                "• 2-3 huevos\n" +
                        "• Avena o pan integral\n" +
                        "• 1 fruta"
        ));

        meals.add(new MealSuggestion(
                "Colación 1",
                "Snack saludable",
                "• Yogur o queso cottage\n" +
                        "• Fruta o nueces"
        ));

        meals.add(new MealSuggestion(
                "Comida",
                "Comida balanceada",
                "• 150-180 g de proteína\n" +
                        "• Arroz, pasta o papa\n" +
                        "• Verduras"
        ));

        meals.add(new MealSuggestion(
                "Colación 2",
                "Snack ligero",
                "• 1 fruta\n" +
                        "• 2 huevos o atún"
        ));

        meals.add(new MealSuggestion(
                "Cena",
                "Cena ligera",
                "• Proteína magra\n" +
                        "• Verduras\n" +
                        "• Porción moderada de carbohidrato"
        ));

        return new MealPlan(
                "Plan alimenticio equilibrado",
                "2200 - 2500 kcal",
                protein + " g de proteína aprox.",
                "Plan general para mantener energía, salud y adherencia diaria.",
                meals
        );
    }
}