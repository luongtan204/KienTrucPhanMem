package com.example;

public class SimpleEnemyFactory {

    public Enemy createEnemy(String type) {
        switch (type.toUpperCase()) {
            case "GOBLIN":
                return new Goblin();
            case "ORC":
                return new Orc();
            case "DRAGON":
                return new Dragon();
            default:
                throw new IllegalArgumentException("Không tìm thấy quái vật: " + type);
        }
    }
}

