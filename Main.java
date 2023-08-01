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

        GaussianBlur gb = new GaussianBlur(img);
        img = gb.gaussianBlur(10);

        try {
            ImageIO.write(img, "png", new File("output/result.png"));
            System.out.println("Image Saved Successfully!");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
    }
}