package com.company;
import java.io.*;

public class abc {
    private String filename;
    private int[]abclist;
    public abc(String filename){
        this.filename = filename;
        this.abclist = new int[26];
    }
    public void show() throws IOException, FileNotFoundException {
        FileInputStream input = new FileInputStream(filename);
        int current = -1;
        while((current=input.read())!=-1) {
            current = Character.toLowerCase(current);
            if (current >= 'a' && current <= 'z') {
                abclist[(int)current - (int)'a']++;
            }
        }
        FileWriter output = new FileWriter("output.txt");
        for(int i=0; i<abclist.length; i++) {
            String out = Character.toString((char) i) + " " + Integer.toString(abclist[i]) + "\n";
            output.write((char) i + 'a');
            output.write('-');
            output.write(new Integer(abclist[i]).toString());
            output.write('\n');
        }
        output.close();
        input.close();
    }

}
