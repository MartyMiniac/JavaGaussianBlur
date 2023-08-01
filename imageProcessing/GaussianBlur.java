package imageProcessing;

import java.awt.image.BufferedImage;

import DataStructures.Pixel;

public class GaussianBlur {
    private int[][] mask = {{1,  4,  7,  4,  1},
                            {4, 16, 26, 16,  7},
                            {7, 16, 26, 16,  7},
                            {4,  7, 16,  7,  4},
                            {1,  4,  7,  4,  1}};
    // private int[][] mask = {{1, 1, 1},
    //                         {1, 1, 1},
    //                         {1, 1, 1}};
    private int maskSum;
    private BufferedImage src = null;
    private BufferedImage dest = null;
    private int width, height;

    public GaussianBlur(BufferedImage bi) {
        this.src = bi;
        this.width=this.src.getWidth();
        this.height=this.src.getHeight();

        this.maskSum=this.calcMaskSum();
        this.dest = this.createBlankImage();
    }

    public BufferedImage gaussianBlur() {
        for(int y=0;  y<this.height; y++) {
            for(int x=0; x<this.width; x++) {
                Pixel px = stepInstruction(x, y);

                this.dest.setRGB(x, y, px.getRGB());
            }
        }

        return this.dest;
    }

    private Pixel stepInstruction(int x, int y) {
        int maskSize = this.mask.length;
        Pixel matpx[][] = new Pixel[maskSize][maskSize];
        int ystart = y-this.mask.length/2;
        int yend = y+this.mask.length/2;
        int xstart = x-this.mask.length/2;
        int xend = x+this.mask.length/2;

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
                matpx[j-xstart][i-ystart].mul((double)this.mask[j-xstart][i-ystart]);
            }
        }

        //average
        int maskTotal=0;
        Pixel totalPixel = new Pixel(0);
        for(int i=0; i<maskSize; i++) {
            for(int j=0; j<maskSize; j++) {
                if(matpx[i][j]!=null) {
                    maskTotal+=this.mask[i][j];
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
    private void setPixel(int x, int y, Pixel px) {
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

    private int calcMaskSum() {
        int sum=0;
        for(int i=0; i<this.mask.length; i++) {
            for(int j=0; j<this.mask[i].length; j++) {
                sum+=this.mask[i][j];
            }
        }
        return sum;
    }
}
