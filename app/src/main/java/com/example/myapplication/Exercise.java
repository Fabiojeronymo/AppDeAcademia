package com.example.myapplication;

public class Exercise {
    private String name;
    private String setsReps; // Para armazenar o número de séries e repetições

    // Construtor
    public Exercise(String name, String setsReps) {
        this.name = name;
        this.setsReps = setsReps;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getSetsReps() {
        return setsReps; // Este é o método que você está tentando acessar
    }
}
