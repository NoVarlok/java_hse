package com.company;

import javax.swing.*;

public class Elevator {
    private int currentFloor, destinationFloor, direction, capacity, currentCapacity;
    private Building building;
    private int[]personDestination;
    private String name;
    Elevator(int height, int capacity, String name, Building building){
        this.currentFloor = 0;
        this.destinationFloor = 0;
        checkDirection();
        this.capacity = capacity;
        this.currentCapacity = 0;
        personDestination = new int[height];
        this.building = building;
        this.name = name;
    }
    private void checkDirection(){
        if(currentCapacity != 0 && currentFloor == destinationFloor){
            for(int i=0;i<personDestination.length;i++){
                if(personDestination[i]!=0){
                    destinationFloor = i;
                    break;
                }
            }
        }
        if(currentFloor < destinationFloor) direction = 1;
        else if(currentFloor > destinationFloor) direction = -1;
        else direction = 0;
    }
    public void move(){
        clearOnFloor();
        if(direction != 0){
            System.out.println("Elevator " + name + " reached floor " + Integer.toString(currentFloor));
            building.elevatorArrived(this);
        }
        currentFloor += direction;
        checkDirection();
    }
    public void addPerson(Person person){
        currentCapacity++;
        personDestination[person.getFinalFloor()]++;
    }
    public void setDestination(int floor){
        if(direction == 1){
            destinationFloor = Math.max(destinationFloor, floor);
        }
        else if (direction == -1){
            destinationFloor = Math.min(destinationFloor, floor);
        }
        else{
            destinationFloor = floor;
            checkDirection();
        }
    }
    public boolean isFull(){
        return capacity == currentCapacity;
    }
    public void clearOnFloor(){
        currentCapacity -= personDestination[currentFloor];
        if(personDestination[currentFloor] != 0) {
            System.out.println(personDestination[currentFloor] + " Person(-s) left the elevator " + name + " on the " + currentFloor + " floor, current capacity = " + currentCapacity);
        }
        personDestination[currentFloor] = 0;
    }
    public boolean isUpward(){
        return direction == 1;
    }
    public boolean isDownward(){
        return direction == -1;
    }
    public int getCurrentFloor() {
        return currentFloor;
    }
    public boolean isFree(){
        return direction == 0;
    }
    public String getName(){
        return name;
    }
}
