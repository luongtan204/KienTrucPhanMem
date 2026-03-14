package com.example;

public class Goblin implements Enemy {
    @Override
    public void attack() {
        System.out.println("Goblin ném đá!");
    }

    @Override
    public void showInfo() {
        System.out.println("Đây là Yêu tinh (Goblin) - Yếu.");
    }
}

