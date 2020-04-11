package com.company;

import java.util.LinkedList;

public class Building {
    public LinkedList<Person>[]upward;
    public LinkedList<Person>[]downward;
    private int height;
    private Controller controller;
    private PersonManager personManager;

    public void addPerson(Person person){
        if(person.getUpward()){
            synchronized (upward){ //upward[person.getStartFloor()]
                upward[person.getStartFloor()].addLast(person);
            }
        }
        else{
            synchronized (downward){ //downward[person.getStartFloor()]
                downward[person.getStartFloor()].addLast(person);
            }
        }
        System.out.println("Person " + person.getName() +
                " wants to get from floor " + person.getStartFloor() + " to floor " + person.getFinalFloor());
    }
    public int getHeight(){
        return height;
    }
    Building(int height){
        this.height = height;
        this.controller = new Controller(this, 1000, "Controller", 1);
        this.personManager = new PersonManager(this, 1000, "Manager");
        upward = new LinkedList[height];
        downward = new LinkedList[height];
        for(int i=0; i<height; i++){
            upward[i] = new LinkedList<Person>();
            downward[i] = new LinkedList<Person>();
        }
    }
    public void runBuilding(){
        personManager.start();
        controller.start();
    }
    public void elevatorArrived(Elevator elevator) {
        if (elevator.isUpward()) {
            synchronized (upward[elevator.getCurrentFloor()]) {
                while (!elevator.isFull() && upward[elevator.getCurrentFloor()].size() != 0) {
                    System.out.println("Person " + upward[elevator.getCurrentFloor()].peekFirst().getName() + " entered " + elevator.getName());
                    elevator.addPerson(upward[elevator.getCurrentFloor()].pollFirst());
                }
            }
        }
        else if(elevator.isDownward()){
            synchronized (downward[elevator.getCurrentFloor()]) {
                while (!elevator.isFull() && downward[elevator.getCurrentFloor()].size() != 0) {
                    System.out.println("Person " + downward[elevator.getCurrentFloor()].peekFirst().getName() + " entered " + elevator.getName());
                    elevator.addPerson(downward[elevator.getCurrentFloor()].pollFirst());
                }
            }
        }
    }
    public boolean isEmptyUpward(int i){
        synchronized (upward[i]){
            return upward[i].size() == 0;
        }
    }
    public boolean isEmptyDownward(int i){
        synchronized (downward[i]){
            return downward[i].size() == 0;
        }
    }
}

