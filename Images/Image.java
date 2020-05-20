package com.company;
import com.sun.java.accessibility.util.SwingEventMonitor;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Calendar;
import javax.imageio.ImageIO;
import java.io.File;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Image {
    private String name;
    private int height, width, pixelBits;
    private String format, path;
    private Date creationTime;
    private long milliseconds, size;
    private BufferedImage image;

    Image(String name, String path) throws IOException {
        this.name = name;
        File file = new File(path);
        this.image = ImageIO.read(file);
        height = this.image.getHeight();
        width = this.image.getWidth();
        String[] splitted = path.split("\\.");
        this.format = splitted[splitted.length - 1];
        this.path = path;
        this.pixelBits = this.image.getColorModel().getPixelSize();
        this.path = path;

        Path filepath = Paths.get(path);
        BasicFileAttributes attr = Files.readAttributes(filepath, BasicFileAttributes.class);
        this.milliseconds = attr.creationTime().to(TimeUnit.MILLISECONDS);
        this.creationTime = new Date(milliseconds);
        this.size = attr.size();
    }

    public String getName(){
        return name;
    }
    public String getFormat() {
        return format;
    }
    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public BufferedImage getImage(){
        return image;
    }
    public long getMilliseconds(){
        return milliseconds;
    }
    public Date getCreationTime(){
        return creationTime;
    }
    public long getSize(){ return size; }
    public String getPath(){ return path; }
    public int getPixelBits(){ return pixelBits; }

    @Override
    public String toString(){
        return String.format("Name: %s, Height: %d, Width: %d, PixelBits: %d, Image size: %d, Image format: %s, Creation data: %s",
                name, height, width, pixelBits, size, format, creationTime);
    }
}
