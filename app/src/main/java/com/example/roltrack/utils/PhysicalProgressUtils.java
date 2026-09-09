package com.example.roltrack.utils;

public class PhysicalProgressUtils {

    public static double calculateBMI(double weightKg, double heightCm) {
        double heightM = heightCm / 100.0;
        if (heightM <= 0) return 0.0;
        return weightKg / (heightM * heightM);
    }

    public static String getBMICategory(double bmi) {
        if (bmi < 18.5) return "Bajo peso";
        if (bmi < 25.0) return "Peso normal";
        if (bmi < 30.0) return "Sobrepeso";
        return "Obesidad";
    }

    public static String getRecommendation(double bmi, String goal) {
        String normalizedGoal = goal == null ? "" : goal.toLowerCase();

        if (bmi < 18.5) {
            return "Se recomienda un enfoque orientado a ganar masa muscular y mejorar la nutrición.";
        }

        if (bmi >= 25.0 && bmi < 30.0) {
            return "Se recomienda priorizar pérdida de grasa moderada, actividad constante y alta adherencia.";
        }

        if (bmi >= 30.0) {
            return "Se recomienda comenzar con progresión gradual, cardio ligero, movilidad y control nutricional.";
        }

        if (normalizedGoal.contains("musculo") || normalizedGoal.contains("músculo") || normalizedGoal.contains("fuerza")) {
            return "Tu perfil es compatible con un enfoque de ganancia muscular controlada.";
        }

        if (normalizedGoal.contains("grasa") || normalizedGoal.contains("bajar") || normalizedGoal.contains("peso")) {
            return "Tu perfil es compatible con una estrategia de recomposición corporal.";
        }

        return "Tu perfil es compatible con un enfoque de condición física general y mantenimiento saludable.";
    }

    public static String getSuggestedFocus(double bmi, String goal) {
        String normalizedGoal = goal == null ? "" : goal.toLowerCase();

        if (bmi < 18.5) return "Enfoque sugerido: volumen limpio";
        if (bmi >= 25.0) return "Enfoque sugerido: reducción de grasa";

        if (normalizedGoal.contains("musculo") || normalizedGoal.contains("músculo")) {
            return "Enfoque sugerido: hipertrofia";
        }

        return "Enfoque sugerido: mantenimiento / condición general";
    }
}