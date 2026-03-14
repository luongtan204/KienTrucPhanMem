package com.example;

public class Dragon implements Enemy {
    @Override
    public void attack() {
        System.out.println("Dragon phun lửa!");
    }

    @Override
    public void showInfo() {
        System.out.println("Đây là Rồng (Dragon) - Mạnh.");
    }
}

