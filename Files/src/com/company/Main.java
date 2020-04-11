package com.company;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        try{
            System.out.println("Enter filepath:");
            Scanner in = new Scanner(System.in);
            String line = in.nextLine();
            abc file = new abc(line);
            file.show();
        }
        catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
