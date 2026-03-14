package com.example;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- BƯỚC 1: TEST SINGLETON ---");
        GameManager manager1 = GameManager.getInstance();
        manager1.setDifficulty("Khó");
        System.out.println("Độ khó hiện tại: " + manager1.getDifficulty());

        System.out.println("\n--- BƯỚC 2 & 3: TEST FACTORY ---");
        SimpleEnemyFactory factory = new SimpleEnemyFactory();

        Enemy goblin = factory.createEnemy("GOBLIN");
        Enemy orc = factory.createEnemy("ORC");
        Enemy dragon = factory.createEnemy("DRAGON");

        goblin.showInfo();
        orc.showInfo();
        dragon.showInfo();

        System.out.println("\n--- BƯỚC 4: CHỨNG MINH SINGLETON ---");
        GameManager manager2 = GameManager.getInstance();
        System.out.println("Độ khó ở manager2 là: " + manager2.getDifficulty());

        if (manager1 == manager2) {
            System.out.println("=> THÀNH CÔNG: manager1 và manager2 là cùng một đối tượng!");
        } else {
            System.out.println("=> THẤT BẠI: Đây là 2 đối tượng khác nhau.");
        }
    }
}

