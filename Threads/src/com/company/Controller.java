package com.company;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class Controller extends Thread {
    private String name;
    private Building building;
    private Elevator[]elevators;
    private int decay;

    Controller(Building building, int decay, String name, int cntElevator){
        this.decay = decay;
        this.building = building;
        this.name = name;
        elevators = new Elevator[cntElevator];
        for(int i=0;i<cntElevator;i++){
            elevators[i]=new Elevator(building.getHeight(), 5, Integer.toString(i), building);
        }
    }
    public void run(){
        while(true) {
            try {
                for(Elevator elevator: elevators){
                    elevator.move();
                }
                synchronized (building.upward) {
                    for (int floor = 0; floor < building.getHeight(); floor++) {
                        if (building.upward[floor].size() != 0) {
                            int toCall = -1;
                            int distance = building.getHeight() + 1;
                            for (int i = 0; i < elevators.length; i++) {
                                if (elevators[i].isFree() || (elevators[i].isUpward() && elevators[i].getCurrentFloor() <= floor)) {
                                    if(elevators[i].isFree() && elevators[i].getCurrentFloor() != floor && building.upward[elevators[i].getCurrentFloor()].size()!=0){
                                        continue;
                                    }
                                    if (distance > Math.abs(elevators[i].getCurrentFloor() - floor)) {
                                        distance = Math.abs(elevators[i].getCurrentFloor() - floor);
                                        toCall = i;
                                    }
                                }
                            }
                            if (toCall != -1) {
                                if(elevators[toCall].getCurrentFloor() != floor) {
                                    elevators[toCall].setDestination(floor);
                                }
                                else{
                                    elevators[toCall].setDestination(building.upward[floor].peekFirst().getFinalFloor());
                                }
                            }
                        }
                    }
                }
                synchronized (building.downward) {
                    for (int floor = 0; floor < building.getHeight(); floor++) {
                        if (building.downward[floor].size() != 0) {
                            int toCall = -1;
                            int distance = building.getHeight() + 1;
                            for (int i = 0; i < elevators.length; i++) {
                                if (elevators[i].isFree() || (elevators[i].isDownward() && elevators[i].getCurrentFloor() >= floor)) {
                                    if(elevators[i].isFree() && elevators[i].getCurrentFloor() != floor && building.downward[elevators[i].getCurrentFloor()].size()!=0){
                                        continue;
                                    }
                                    if (distance > Math.abs(elevators[i].getCurrentFloor() - floor)) {
                                        distance = Math.abs(elevators[i].getCurrentFloor() - floor);
                                        toCall = i;
                                    }
                                }
                            }
                            if (toCall != -1) {
                                if(elevators[toCall].getCurrentFloor() != floor) {
                                    elevators[toCall].setDestination(floor);
                                }
                                else{
                                    elevators[toCall].setDestination(building.downward[floor].peekFirst().getFinalFloor());
                                }
                            }
                        }
                    }
                }
                Thread.sleep(decay);
            }
            catch (InterruptedException e){
                System.out.println("Controller " + name +" stopped");
            }
        }
    }
}
