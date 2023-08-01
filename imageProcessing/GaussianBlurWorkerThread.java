package imageProcessing;

import DataStructures.Pixel;

public class GaussianBlurWorkerThread extends Thread {
    private GaussianBlur gbir;
    private int width, y;
    
    public GaussianBlurWorkerThread(GaussianBlur gaussianBlurInstanceReference, int width, int y) {
        this.gbir=gaussianBlurInstanceReference;
        this.width=width;
        this.y=y;
    }

    public void run() {
        for(int x=0; x<this.width; x++) {
            Pixel px = gbir.stepInstruction(x, y);
            gbir.setPixel(x, y, px);
        }
    }

    //range of pixels the worker is responsible for
    public String getPos() {
        return "("+0+","+this.y+")"+" - "+"("+this.width+","+this.y+")";
    }
}
