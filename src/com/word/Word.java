package com.word;


import java.util.Random;


public class Word {

    private int pointValue;
    private int fallingSpeed;
    private StringBuilder text;
    private int xPos;
    private int yPos;
    boolean isAlive;

    private final Random rand = new Random();
    private final int RANGE_X = 100;
    private final int RANGE_Y = 100;
    private final static double LEFT_MOVE_CHANCE = 0.5;
    private final static int FALL_Y_SPEED = 20;


    private boolean negative() {
        return Math.random() < LEFT_MOVE_CHANCE;
    }
    //need to change String difficult to Enum difficulty.
    public void fall(int fallingSpeed){
        int deltaX = rand.nextInt(RANGE_X) + 1;
        if (negative()) deltaX = -deltaX;
        xPos = xPos + deltaX;
        yPos = yPos + FALL_Y_SPEED;  // fixed y drop seems better


    }

    public void changeColor(){
    }

    public  void makeDisappear(){
    }

    public int getPointValue() {
        return pointValue;
    }

    public int getFallingSpeed() {
        return fallingSpeed;
    }

    public StringBuilder getText() {
        return text;
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public boolean isAlive() {
        return isAlive;
    }

}