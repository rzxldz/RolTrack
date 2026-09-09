package com.example.roltrack.utils;

public class HealthUtils {

    public static int calculatePercent(int value, int goal) {
        if (goal <= 0) return 0;
        int percent = (value * 100) / goal;
        return Math.min(percent, 100);
    }

    public static int calculatePercentDouble(double value, int goal) {
        if (goal <= 0) return 0;
        int percent = (int) ((value * 100.0) / goal);
        return Math.min(percent, 100);
    }

    public static int calculateDailyScore(int waterPercent, int nutritionPercent, int sleepPercent, int exercisePercent) {
        return (waterPercent + nutritionPercent + sleepPercent + exercisePercent) / 4;
    }

    public static String getMotivationalMessage(int score) {
        if (score >= 90) return "Estás teniendo un día excelente. Sigue así.";
        if (score >= 75) return "Muy buen progreso hoy. Mantén el enfoque.";
        if (score >= 50) return "Vas avanzando. Completa uno o dos hábitos más.";
        if (score >= 25) return "Buen inicio. Todavía puedes levantar mucho tu día.";
        return "Empieza con una acción simple y construye desde ahí.";
    }

    public static int countCompletedHabitsToday(int water, int calories, double sleep, int exercise) {
        int count = 0;

        if (water > 0) count++;
        if (calories > 0) count++;
        if (sleep > 0) count++;
        if (exercise > 0) count++;

        return count;
    }

    public static String getHabitStreakMessage(int completedHabits) {
        if (completedHabits == 4) return "Excelente, completaste todos tus hábitos de hoy.";
        if (completedHabits == 3) return "Vas muy bien, solo te falta un hábito.";
        if (completedHabits == 2) return "Buen avance, sigue construyendo consistencia.";
        if (completedHabits == 1) return "Buen inicio, intenta completar más hábitos hoy.";
        return "Empieza hoy registrando al menos un hábito.";
    }

    public static String getGreetingMessage(String name) {
        if (name == null || name.trim().isEmpty()) {
            return "Hola";
        }
        return "Hola, " + name;
    }

    public static String getDailyFocusMessage(int score, int completedHabits) {
        if (score >= 85) return "Tu enfoque de hoy es mantener el gran ritmo que llevas.";
        if (score >= 60) return "Tu enfoque de hoy es cerrar fuerte y completar tus hábitos faltantes.";
        if (completedHabits == 0) return "Tu enfoque de hoy es empezar con una acción pequeña y sostenerla.";
        return "Tu enfoque de hoy es construir consistencia paso a paso.";
    }
}