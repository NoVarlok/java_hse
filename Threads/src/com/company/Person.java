package com.company;

import java.util.Random;

public class Person {
    private int startFloor, finalFloor;
    private boolean upward;
    private String name;

    public int getFinalFloor() {
        return finalFloor;
    }
    public int getStartFloor() {
        return startFloor;
    }
    public boolean getUpward(){
        return upward;
    }
    public String getName() { return name; }

    Person(int maxFloor, String name){
        this.name = name;
        this.startFloor = new Random().nextInt(maxFloor);
        do{
            this.finalFloor = new Random().nextInt(maxFloor);
        }while(this.finalFloor == this.startFloor);
        upward = this.finalFloor > this.startFloor;
    }
    Person(int maxFloor, int name){
        this(maxFloor, Integer.toString(name));
    }

}
