package com.word;

import com.word.util.Color;


public class Word {

    private int pointValue;
    private int fallingSpeed;
    private StringBuilder text;
    private int xPos;
    private int yPos;
    boolean isAlive;
    Color color;


    //need to change String difficult to Enum difficulty.
    public StringBuilder fall(String word, int fallingSpeed){
            int randomNumberOfSpaces = (int) (Math.random() * 20 + 1);
            StringBuilder line = new StringBuilder();
            for (int i = 0; i < randomNumberOfSpaces; i++){
                line.append(" ");
            }
            line.append(word);
            if (fallingSpeed == 1){ //1 is easy
                line.append("\n");
            } else if (fallingSpeed == 2){ //2 is medium
                line.append("\n\n");
            } else {
                line.append("\n\n\n");
            }
            return line;
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

    public Color getColor() {
        return color;
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