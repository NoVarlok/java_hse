package com.company;

import java.util.Random;

public class PersonManager extends Thread{
    private Building building;
    private int decay, personCounter;
    private String name;
    PersonManager(Building building, int decay, String name){
        this.building = building;
        this.decay = decay;
        this.name = name;
        this.personCounter = 0;
    }
    public void run(){
        while(true) {
            try {
                if(new Random().nextInt(3) == 0) {
                    addPerson();
                }
                Thread.sleep(decay);
            }
            catch (InterruptedException e){
                System.out.println("PersonManager " + name +" stopped");
            }
        }
    }
    public void addPerson(){
        Person person = new Person(building.getHeight(), personCounter);
        personCounter++;
        building.addPerson(person);
    }
}
