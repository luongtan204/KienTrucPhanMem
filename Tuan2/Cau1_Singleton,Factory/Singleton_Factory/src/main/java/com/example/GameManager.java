package com.example;

public class GameManager {
    private static GameManager instance;

    private String difficulty;
    private int score;

    private GameManager() {
        this.difficulty = "Bình thường";
        this.score = 0;
    }

    public static GameManager getInstance() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDifficulty() {
        return difficulty;
    }
}

