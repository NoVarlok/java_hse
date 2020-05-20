package com.company;


import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.stream.ImageInputStream;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.sql.SQLSyntaxErrorException;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        ImageLibrary imageLibrary = new ImageLibrary();
        Scanner in = new Scanner(System.in);
        while(true) {
            System.out.println("Select an Operation:");
            System.out.println("0: Exit");
            System.out.println("1: Add Image");
            System.out.println("2: Delete Image");
            System.out.println("3: Sort by");
            System.out.println("4: Calculate Similarity");
            System.out.println("5: Show Name List");
            System.out.println("6: Describe Image");
            System.out.println("7: Describe All Images");
            System.out.println("8: Show Image");
            try {
                int option = Integer.parseInt(in.nextLine());
                try {
                    switch (option) {
                        case 0:
                            System.exit(0);
                        case 1:
                            System.out.println("Enter image name:");
                            String name_1 = in.nextLine();
                            System.out.println("Enter image path:");
                            String path_1 = in.nextLine();
                            imageLibrary.add(name_1, path_1);
                            break;
                        case 2:
                            System.out.println("Enter image name:");
                            String name_2 = in.nextLine();
                            imageLibrary.delete(name_2);
                            break;
                        case 3:
                            imageLibrary.sortBy();
                            break;
                        case 4:
                            System.out.println("Enter image name:");
                            imageLibrary.similarWarp();
                            break;
                        case 5:
                            imageLibrary.showNameList();
                            break;
                        case 6:
                            System.out.println("Enter image name:");
                            String name_6 = in.nextLine();
                            imageLibrary.describe(name_6);
                            break;
                        case 7:
                            imageLibrary.describeAll();
                            break;
                        case 8:
                            System.out.println("Enter image name:");
                            String name_8 = in.nextLine();
                            imageLibrary.showImage(name_8);
                        default:
                            System.out.println("Unknown option was selected");
                    }
                } catch (IOException e) {
                    System.out.println("An Error Occurred");
                    System.out.println(e.getMessage());
                }
            }
            catch (java.lang.NumberFormatException e){
                System.out.println("Unknown option was selected");
            }
            System.out.println();
        }
    }
}
