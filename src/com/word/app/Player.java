package com.word.app;

public class Player {

    private static int score;
    private int idCount;
    private int id;
    private String name;

    public Player(String name) {
        setName(name);
        this.score = 0;
        id = idCount++;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getScore() {
        return score;
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