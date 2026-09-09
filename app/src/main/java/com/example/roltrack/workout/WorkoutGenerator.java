package com.example.roltrack.workout;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WorkoutGenerator {

    public static WorkoutPlan generate(double weight, double heightCm, int age, String goal) {
        return generateAdvanced(weight, heightCm, age, goal, "Automático", "Automático", "Automático");
    }

    public static WorkoutPlan generateAdvanced(double weight, double heightCm, int age,
                                               String profileGoal, String manualGoal,
                                               String type, String level) {

        double heightM = heightCm / 100.0;
        double bmi = weight / (heightM * heightM);

        String normalizedProfileGoal = profileGoal == null ? "" : profileGoal.toLowerCase(Locale.ROOT);
        String normalizedManualGoal = manualGoal == null ? "automático" : manualGoal.toLowerCase(Locale.ROOT);
        String normalizedType = type == null ? "automático" : type.toLowerCase(Locale.ROOT);
        String normalizedLevel = level == null ? "automático" : level.toLowerCase(Locale.ROOT);

        String effectiveGoal;
        if (normalizedManualGoal.equals("automático")) {
            effectiveGoal = normalizedProfileGoal;
        } else {
            effectiveGoal = normalizedManualGoal;
        }

        boolean wantsMuscle = effectiveGoal.contains("músculo")
                || effectiveGoal.contains("musculo")
                || effectiveGoal.contains("fuerza");

        boolean wantsWeightLoss = effectiveGoal.contains("bajar")
                || effectiveGoal.contains("peso")
                || effectiveGoal.contains("grasa");

        boolean wantsGeneral = effectiveGoal.contains("condición")
                || effectiveGoal.contains("condicion")
                || effectiveGoal.contains("general");

        if (normalizedType.equals("automático")) {
            if (wantsMuscle) {
                normalizedType = "gym";
            } else if (wantsWeightLoss || bmi >= 27) {
                normalizedType = "calistenia";
            } else if (wantsGeneral) {
                normalizedType = "gym";
            } else {
                normalizedType = "gym";
            }
        }

        if (normalizedLevel.equals("automático")) {
            if (age < 18 || bmi >= 30 || weight >= 100) {
                normalizedLevel = "principiante";
            } else if (age <= 35 && bmi < 28) {
                normalizedLevel = "intermedio";
            } else {
                normalizedLevel = "principiante";
            }
        }

        if (normalizedType.equals("gym")) {
            if (normalizedLevel.equals("principiante")) {
                return buildGymBeginner(wantsWeightLoss, wantsMuscle);
            } else if (normalizedLevel.equals("intermedio")) {
                return buildGymIntermediate(wantsWeightLoss, wantsMuscle);
            } else {
                return buildGymAdvanced(wantsWeightLoss, wantsMuscle);
            }
        }

        if (normalizedType.equals("calistenia")) {
            if (normalizedLevel.equals("principiante")) {
                return buildCalisthenicsBeginner(wantsWeightLoss, wantsMuscle);
            } else if (normalizedLevel.equals("intermedio")) {
                return buildCalisthenicsIntermediate(wantsWeightLoss, wantsMuscle);
            } else {
                return buildCalisthenicsAdvanced(wantsWeightLoss, wantsMuscle);
            }
        }

        return buildGymBeginner(wantsWeightLoss, wantsMuscle);
    }

    private static WorkoutPlan buildGymBeginner(boolean wantsWeightLoss, boolean wantsMuscle) {
        List<WorkoutDay> days = new ArrayList<>();

        if (wantsWeightLoss) {
            days.add(new WorkoutDay("Día 1 – Pierna + cardio",
                    "• Sentadilla 4x10\n• Prensa 3x12\n• Peso muerto rumano 3x10\n• Caminadora 15 min"));
            days.add(new WorkoutDay("Día 2 – Pecho y espalda",
                    "• Press banca 3x10\n• Jalón al pecho 3x12\n• Remo sentado 3x12\n• Plancha 3x30 seg"));
            days.add(new WorkoutDay("Día 3 – Cardio + core",
                    "• Bicicleta 20 min\n• Crunch 3x20\n• Mountain climbers 3x20\n• Plancha lateral 3x20 seg"));
            days.add(new WorkoutDay("Día 4 – Full body",
                    "• Sentadilla goblet 3x12\n• Press mancuerna 3x12\n• Remo mancuerna 3x12\n• Elíptica 10 min"));

            return new WorkoutPlan(
                    "Rutina gym para pérdida de grasa",
                    "Gym",
                    "Principiante",
                    "4 días por semana",
                    "Enfocada en gasto calórico, fuerza básica y adaptación al gimnasio.",
                    days
            );
        }

        if (wantsMuscle) {
            days.add(new WorkoutDay("Día 1 – Pecho y tríceps",
                    "• Press banca 4x8\n• Press inclinado 3x10\n• Fondos asistidos 3x10\n• Tríceps polea 3x12"));
            days.add(new WorkoutDay("Día 2 – Espalda y bíceps",
                    "• Jalón al pecho 4x10\n• Remo sentado 4x10\n• Curl bíceps 3x12\n• Face pulls 3x15"));
            days.add(new WorkoutDay("Día 3 – Pierna",
                    "• Sentadilla 4x8\n• Prensa 4x10\n• Curl femoral 3x12\n• Pantorrilla 4x15"));
            days.add(new WorkoutDay("Día 4 – Hombro y core",
                    "• Press militar 4x10\n• Elevaciones laterales 3x15\n• Crunch 3x20\n• Plancha 3x40 seg"));

            return new WorkoutPlan(
                    "Rutina gym para masa muscular",
                    "Gym",
                    "Principiante",
                    "4 días por semana",
                    "Diseñada para aprender movimientos base y ganar músculo de forma progresiva.",
                    days
            );
        }

        days.add(new WorkoutDay("Día 1 – Superior",
                "• Press pecho 3x12\n• Jalón al pecho 3x12\n• Remo 3x12"));
        days.add(new WorkoutDay("Día 2 – Inferior",
                "• Sentadilla 3x12\n• Prensa 3x12\n• Peso muerto rumano 3x10"));
        days.add(new WorkoutDay("Día 3 – Cardio y core",
                "• Caminadora 20 min\n• Crunch 3x20\n• Plancha 3x30 seg"));
        days.add(new WorkoutDay("Día 4 – Full body",
                "• Sentadilla goblet 3x12\n• Press mancuerna 3x12\n• Remo mancuerna 3x12"));

        return new WorkoutPlan(
                "Rutina gym general",
                "Gym",
                "Principiante",
                "4 días por semana",
                "Rutina equilibrada para salud general y adaptación al entrenamiento.",
                days
        );
    }

    private static WorkoutPlan buildGymIntermediate(boolean wantsWeightLoss, boolean wantsMuscle) {
        List<WorkoutDay> days = new ArrayList<>();

        if (wantsWeightLoss) {
            days.add(new WorkoutDay("Día 1 – Pierna intensa",
                    "• Sentadilla 4x8\n• Prensa 4x12\n• Peso muerto rumano 4x8\n• HIIT 10 min"));
            days.add(new WorkoutDay("Día 2 – Push",
                    "• Press banca 4x8\n• Press inclinado 3x10\n• Press hombro 3x10\n• Fondos 3x10"));
            days.add(new WorkoutDay("Día 3 – Pull",
                    "• Dominadas asistidas 4x8\n• Remo barra 4x8\n• Jalón cerrado 3x10\n• Curl 3x12"));
            days.add(new WorkoutDay("Día 4 – Metabólico",
                    "• Sentadilla goblet 3x15\n• Press mancuerna 3x12\n• Remo mancuerna 3x12\n• Elíptica 15 min"));
            days.add(new WorkoutDay("Día 5 – Core + cardio",
                    "• Crunch 3x20\n• Plancha 4x40 seg\n• Mountain climbers 3x25\n• Bici 20 min"));

            return new WorkoutPlan(
                    "Rutina gym intermedia para definición",
                    "Gym",
                    "Intermedio",
                    "5 días por semana",
                    "Mezcla fuerza y trabajo metabólico para mejorar composición corporal.",
                    days
            );
        }

        if (wantsMuscle) {
            days.add(new WorkoutDay("Día 1 – Push",
                    "• Press banca 4x6-8\n• Press inclinado 3x8-10\n• Press militar 3x8-10\n• Tríceps cuerda 3x12"));
            days.add(new WorkoutDay("Día 2 – Pull",
                    "• Dominadas asistidas 4x6-8\n• Remo barra 4x8\n• Jalón cerrado 3x10\n• Curl bíceps 3x12"));
            days.add(new WorkoutDay("Día 3 – Legs",
                    "• Sentadilla 4x6-8\n• Peso muerto rumano 4x8\n• Curl femoral 3x12\n• Prensa 3x12"));
            days.add(new WorkoutDay("Día 4 – Upper",
                    "• Press plano 3x10\n• Remo sentado 3x10\n• Elevaciones laterales 3x15\n• Face pulls 3x15"));
            days.add(new WorkoutDay("Día 5 – Arms + core",
                    "• Curl barra 4x10\n• Extensión tríceps 4x12\n• Hammer curl 3x12\n• Crunch 3x20"));

            return new WorkoutPlan(
                    "Rutina gym intermedia para hipertrofia",
                    "Gym",
                    "Intermedio",
                    "5 días por semana",
                    "Orientada a volumen moderado y ganancia muscular.",
                    days
            );
        }

        days.add(new WorkoutDay("Día 1 – Push", "• Press banca 4x8\n• Press inclinado 3x10\n• Press hombro 3x10"));
        days.add(new WorkoutDay("Día 2 – Pull", "• Remo 4x8\n• Jalón 3x10\n• Curl 3x12"));
        days.add(new WorkoutDay("Día 3 – Legs", "• Sentadilla 4x8\n• Prensa 3x12\n• Peso muerto rumano 3x10"));
        days.add(new WorkoutDay("Día 4 – Cardio + core", "• Bicicleta 20 min\n• Crunch 3x20\n• Plancha 3x40 seg"));

        return new WorkoutPlan(
                "Rutina gym intermedia general",
                "Gym",
                "Intermedio",
                "4 días por semana",
                "Balance entre fuerza, acondicionamiento y control corporal.",
                days
        );
    }

    private static WorkoutPlan buildGymAdvanced(boolean wantsWeightLoss, boolean wantsMuscle) {
        return buildGymIntermediate(wantsWeightLoss, true);
    }

    private static WorkoutPlan buildCalisthenicsBeginner(boolean wantsWeightLoss, boolean wantsMuscle) {
        List<WorkoutDay> days = new ArrayList<>();

        if (wantsWeightLoss) {
            days.add(new WorkoutDay("Día 1 – Full body",
                    "• Sentadillas 4x15\n• Lagartijas inclinadas 4x10\n• Puente glúteo 4x15\n• Caminata 20 min"));
            days.add(new WorkoutDay("Día 2 – Core + cardio",
                    "• Crunch 3x20\n• Plancha 3x30 seg\n• Mountain climbers 3x20\n• Jumping jacks 3x30 seg"));
            days.add(new WorkoutDay("Día 3 – Tren superior",
                    "• Lagartijas inclinadas 4x10\n• Remo con mochila 4x12\n• Fondos en banco 3x10"));
            days.add(new WorkoutDay("Día 4 – Pierna",
                    "• Sentadilla 4x15\n• Desplantes 3x12 por pierna\n• Step-ups 3x12"));

            return new WorkoutPlan(
                    "Rutina calistenia para pérdida de grasa",
                    "Calistenia",
                    "Principiante",
                    "4 días por semana",
                    "Rutina ligera-media enfocada en movilidad, activación y gasto calórico.",
                    days
            );
        }

        if (wantsMuscle) {
            days.add(new WorkoutDay("Día 1 – Empuje",
                    "• Lagartijas 4x10\n• Fondos en banco 4x12\n• Pike push-ups 3x10"));
            days.add(new WorkoutDay("Día 2 – Tirón",
                    "• Remo con mochila 4x12\n• Curl mochila 3x12\n• Superman hold 3x20 seg"));
            days.add(new WorkoutDay("Día 3 – Pierna",
                    "• Sentadilla 4x15\n• Desplantes 3x12\n• Puente glúteo 4x15"));
            days.add(new WorkoutDay("Día 4 – Full body",
                    "• Lagartijas 3x10\n• Sentadillas 3x15\n• Plancha 3x40 seg"));

            return new WorkoutPlan(
                    "Rutina calistenia para fuerza base",
                    "Calistenia",
                    "Principiante",
                    "4 días por semana",
                    "Ideal para desarrollar control corporal y fuerza funcional inicial.",
                    days
            );
        }

        days.add(new WorkoutDay("Día 1 – Empuje", "• Lagartijas 4x10\n• Fondos en banco 3x12\n• Plancha 3x30 seg"));
        days.add(new WorkoutDay("Día 2 – Pierna", "• Sentadillas 4x15\n• Desplantes 3x12\n• Puente glúteo 3x15"));
        days.add(new WorkoutDay("Día 3 – Tirón", "• Remo mochila 4x12\n• Curl mochila 3x12\n• Superman 3x20 seg"));
        days.add(new WorkoutDay("Día 4 – Full body", "• Jumping jacks 3x30 seg\n• Sentadilla 3x15\n• Lagartijas 3x8"));

        return new WorkoutPlan(
                "Rutina calistenia general",
                "Calistenia",
                "Principiante",
                "4 días por semana",
                "Rutina equilibrada para salud general y fuerza básica.",
                days
        );
    }

    private static WorkoutPlan buildCalisthenicsIntermediate(boolean wantsWeightLoss, boolean wantsMuscle) {
        List<WorkoutDay> days = new ArrayList<>();

        days.add(new WorkoutDay("Día 1 – Empuje",
                "• Lagartijas declinadas 4x12\n• Fondos 4x10\n• Pike push-ups 4x10"));
        days.add(new WorkoutDay("Día 2 – Tirón",
                "• Remo invertido 4x10\n• Remo mochila pesado 4x12\n• Curl mochila 3x15"));
        days.add(new WorkoutDay("Día 3 – Pierna",
                "• Sentadilla búlgara 4x10\n• Jump squats 3x15\n• Puente glúteo una pierna 3x12"));
        days.add(new WorkoutDay("Día 4 – Core + cardio",
                "• Plancha 4x45 seg\n• Mountain climbers 4x25\n• Crunch 4x20\n• Burpees 3x12"));

        return new WorkoutPlan(
                wantsWeightLoss ? "Rutina calistenia intermedia para definición"
                        : "Rutina calistenia intermedia",
                "Calistenia",
                "Intermedio",
                "4 días por semana",
                "Mayor intensidad y volumen para mejorar condición física y fuerza relativa.",
                days
        );
    }

    private static WorkoutPlan buildCalisthenicsAdvanced(boolean wantsWeightLoss, boolean wantsMuscle) {
        return buildCalisthenicsIntermediate(wantsWeightLoss, wantsMuscle);
    }
}