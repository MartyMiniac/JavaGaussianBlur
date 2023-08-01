package imageProcessing;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import DataStructures.Pixel;

public class GaussianBlur {
    private static int[][] mask = {{1,  4,  7,  4,  1},
                                   {4, 16, 26, 16,  7},
                                   {7, 16, 26, 16,  7},
                                   {4,  7, 16,  7,  4},
                                   {1,  4,  7,  4,  1}};
    // private int[][] mask = {{1, 1, 1},
    //                         {1, 1, 1},
    //                         {1, 1, 1}};
    private BufferedImage src = null;
    private BufferedImage dest = null;
    private int width, height;

    public GaussianBlur(BufferedImage bi) {
        this.src = bi;
        this.width=this.src.getWidth();
        this.height=this.src.getHeight();

        this.dest = this.createBlankImage();
    }

    public BufferedImage gaussianBlur() {
        Queue<GaussianBlurWorkerThread> threadQueue = new LinkedList<>();
        for(int y=0;  y<this.height; y++) {
            GaussianBlurWorkerThread thread = new GaussianBlurWorkerThread(this, this.width, y);
            thread.start();
            threadQueue.add(thread);
        }

        while(!threadQueue.isEmpty()) {
            try {
                threadQueue.peek().join();
                threadQueue.remove();
            }
            catch(InterruptedException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        return this.dest;
    }

    public Pixel stepInstruction(int x, int y) {
        int maskSize = GaussianBlur.mask.length;
        Pixel matpx[][] = new Pixel[maskSize][maskSize];
        int ystart = y-GaussianBlur.mask.length/2;
        int yend = y+GaussianBlur.mask.length/2;
        int xstart = x-GaussianBlur.mask.length/2;
        int xend = x+GaussianBlur.mask.length/2;

        // for(int i=0; i<maskSize; i++) {
        //     for(int j=0; j<maskSize; j++) {
        //         matpx[i][j]=new Pixel();
        //     }
        // }
        for(int i=ystart; i<=yend; i++) {
            for(int j=xstart; j<=xend; j++) {
                if(i<0 || i>=height) {
                    continue;
                }
                if(j<0 || j>=width) {
                    continue;
                }
                matpx[j-xstart][i-ystart] = getPixel(j, i);
                matpx[j-xstart][i-ystart].mul((double)GaussianBlur.mask[j-xstart][i-ystart]);
            }
        }

        //average
        int maskTotal=0;
        Pixel totalPixel = new Pixel(0);
        for(int i=0; i<maskSize; i++) {
            for(int j=0; j<maskSize; j++) {
                if(matpx[i][j]!=null) {
                    maskTotal+=GaussianBlur.mask[i][j];
                    totalPixel.add(matpx[i][j]);
                }
            }
        }

        totalPixel.div((double)maskTotal);

        return totalPixel;
    }

    private Pixel getPixel(int x, int y) {
        return new Pixel(this.src.getRGB(x, y));
    }
    public void setPixel(int x, int y, Pixel px) {
        this.dest.setRGB(x, y, px.getRGB());
    }
    

    //create blank image
    private BufferedImage createBlankImage() {
        int width = this.width;
        int height = this.height;

        BufferedImage dest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for(int y=0;  y<height; y++) {
            for(int x=0; x<width; x++) {
                dest.setRGB(x, y, 0);
            }
        }
        return dest;
    }
}
