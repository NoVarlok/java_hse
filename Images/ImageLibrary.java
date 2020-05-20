package com.company;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.lang.model.type.NullType;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

class Pair{
    float similarity;
    String name;
    Pair(float similarity, String name){
        this.similarity = similarity;
        this.name = name;
    }
}

public class ImageLibrary {
    private HashMap<String, Image> images;

    ImageLibrary() {
        images = new HashMap<String, Image>();
    }

    public void add(String name, String path) {
        try {
            Image addImage = new Image(name, path);
            if (!images.containsKey(name)) {
                images.put(name, addImage);
            } else {
                System.out.printf("Image with name '%s' already exist, Enter 'Y' if you want to replace it\n", name);
                Scanner in = new Scanner(System.in);
                String answer = in.nextLine();
                if (answer.equals("Y")) {
                    images.put(name, addImage);
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void delete(String name) {
        images.remove(name);
    }

    public void sortBy() {
        System.out.println("Choose field for sorting:");
        System.out.println("1: Name");
        System.out.println("2: Height");
        System.out.println("3: Width");
        System.out.println("4: Format");
        System.out.println("5: Creation time");
        System.out.println("6: Size");
        System.out.println("7: PixelBits");
        Scanner in = new Scanner(System.in);
        try {
            int option = Integer.parseInt(in.nextLine());
            ArrayList<Image> imageList = new ArrayList<>(images.values());
            switch (option) {
                case 1:
                    imageList.sort((image1, image2) -> image1.getName().compareTo(image2.getName()));
                    break;
                case 2:
                    imageList.sort((image1, image2) -> Integer.compare(image1.getHeight(), image2.getHeight()));
                    break;
                case 3:
                    imageList.sort((image1, image2) -> Integer.compare(image1.getWidth(), image2.getWidth()));
                    break;
                case 4:
                    imageList.sort((image1, image2) -> image1.getFormat().compareTo(image2.getFormat()));
                    break;
                case 5:
                    imageList.sort((image1, image2) -> Long.compare(image1.getMilliseconds(), image2.getMilliseconds()));
                    break;
                case 6:
                    imageList.sort((image1, image2) -> Long.compare(image1.getSize(), image2.getSize()));
                    break;
                case 7:
                    imageList.sort((image1, image2) -> Integer.compare(image1.getPixelBits(), image2.getPixelBits()));
                    break;
                default:
                    System.out.println("Unknown operation");
                    return;
            }
            imageList.forEach(System.out::println);
        } catch (NumberFormatException exception) {
            System.out.println("Unknown operation");
        }
    }

    public void similarWarp() throws IOException {
        Scanner in = new Scanner(System.in);
        String name = in.nextLine();
        if (images.containsKey(name)) {
            ArrayList<Pair> similarity = similar(name);
            for (Pair item : similarity) {
                System.out.printf("%s: %f\n", item.name, item.similarity);
            }
        } else {
            System.out.println("Unknown image name");
        }
    }

    public float difference(BufferedImage sourceImage, String comparePath) throws IOException {
        File compareFile = new File(comparePath);
        BufferedImage compareImage = ImageIO.read(compareFile);
        BufferedImage resizedImage = new BufferedImage(sourceImage.getWidth(), sourceImage.getHeight(), compareImage.getType());
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(compareImage, 0, 0, sourceImage.getWidth(), sourceImage.getHeight(), null);
        g2d.dispose();
        float diff = 0;
        for (int x = 0; x < sourceImage.getWidth(); x++) {
            for (int y = 0; y < sourceImage.getHeight(); y++) {
                Color soursePixel = new Color(sourceImage.getRGB(x, y));
                Color comparePixel = new Color(resizedImage.getRGB(x, y));
                float diffRed = Math.abs(soursePixel.getRed() - comparePixel.getRed()) / 255.0f / 4.0f;
                float diffGreen = Math.abs(soursePixel.getGreen() - comparePixel.getGreen()) / 255.0f / 4.0f;
                float diffBlue = Math.abs(soursePixel.getBlue() - comparePixel.getBlue()) / 255.0f / 4.0f;
                float diffAlpha = Math.abs(soursePixel.getAlpha() - comparePixel.getAlpha()) / 255.0f / 4.0f;
                diff += diffRed + diffGreen + diffBlue + diffAlpha;
            }
        }
        float similariry = 1 - diff / sourceImage.getHeight() / sourceImage.getWidth();
        return 1 - diff / sourceImage.getHeight() / sourceImage.getWidth();
    }

    public ArrayList<Pair> similar(String name) throws IOException {
        ArrayList<Pair> similaryArray = new ArrayList<>();
        for (String key : images.keySet()) {
            similaryArray.add(new Pair(difference(images.get(name).getImage(), images.get(key).getPath()), key));
        }
        similaryArray.sort((pair1, pair2) -> -Float.compare(pair1.similarity, pair2.similarity));
        return similaryArray;
    }

    public void showNameList() {
        images.keySet().forEach(System.out::println);
    }

    public void describe(String name) {
        if (images.containsKey(name)) {
            System.out.println(images.get(name));
        } else {
            System.out.println("No image with this name");
        }
    }

    public void describeAll() {
        images.values().forEach(System.out::println);
    }

    public void showImage(String name) {
        if (images.containsKey(name)) {
            String path = images.get(name).getPath();
            JFrame frame = new JFrame();
            ImageIcon icon = new ImageIcon(path);
            JLabel label = new JLabel(icon);
            frame.add(label);
            //rame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        }
    }
}