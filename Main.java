import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import imageProcessing.GaussianBlur;

public class Main {
    public static void main(String args[]) {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("_images/img.jpg"));
        }
        catch(IOException e) {
            e.printStackTrace();
            return;
        }

        int CONVOLUTIONS = 10;
        for(int i=0; i<CONVOLUTIONS; i++) {
            System.out.println("Starting Iteration "+(i+1)+"...");
            GaussianBlur gb = new GaussianBlur(img);
            img = gb.gaussianBlur();
            System.out.println("Iteration "+(i+1)+" Completed!");
        }
        try {
            ImageIO.write(img, "png", new File("result.png"));
            System.out.println("Image Saved Successfully!");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}