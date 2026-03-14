package com.example;

public class Orc implements Enemy {
    @Override
    public void attack() {
        System.out.println("Orc chém rìu!");
    }

    @Override
    public void showInfo() {
        System.out.println("Đây là Chằn tinh (Orc) - Trung bình.");
    }
}

