package com.word.app;

public class Player {

    private int score;
    private String name;
    private int idCount;
    private final int id;

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

    public int getScore() {
        return score;
    }

    public void setScore(int score){
        this.score = score;
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