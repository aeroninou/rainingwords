package com.word.app;

public class Player {

    private static int score;
    private static String name;
    private int idCount;
    private int id;

    public Player(String name) {
        setName(name);
        this.score = 0;
        id = idCount++;
    }

    public int getId() {
        return id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        Player.name = name;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score){
        Player.score = score;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " +
                "id=" + getId() +
                ", name='" + getName() + '\'' +
                ", score=" + getScore() +
                '}';
    }
}