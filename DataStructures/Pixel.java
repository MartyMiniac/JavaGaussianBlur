package DataStructures;

public class Pixel {
    private int r, g, b, rgb;
    public Pixel(int r, int g, int b) {
        this.r=r;
        this.b=b;
        this.g=g;
        this.rgb=_setRGB(r, g, b);
    }
    public Pixel(int rgb) {
        this.r=Pixel.getRed(rgb);
        this.g=Pixel.getGreen(rgb);
        this.b=Pixel.getBlue(rgb);
        this.rgb=rgb;
    }
    public Pixel() {
        this.r=0;
        this.g=0;
        this.b=0;
        this.rgb=0;
    }

    public void setRed(int red) {
        this.r=red;
        this.updateRGB();
    }
    public int getRed() {
        return Pixel.boundedIndividual(this.r);
    }
    public void setGreen(int green) {
        this.g=green;
        this.updateRGB();
    }
    public int getGreen() {
        return Pixel.boundedIndividual(this.g);
    }
    public void setBlue(int blue) {
        this.b=blue;
        this.updateRGB();
    }
    public int getBlue() {
        return Pixel.boundedIndividual(this.b);
    }
    public void setRGB(int rgb) {
        this.rgb=rgb;
        this.updateIndividualRGB();
    }
    public void setRGB(int r, int g, int b) {
        this.r=r;
        this.g=g;
        this.b=b;
        this.updateRGB();
    }
    public int getRGB() {
        return this.rgb;
    }
    public void add(int num) {
        this.r+=num;
        this.g+=num;
        this.b+=num;
        this.updateRGB();
    }
    public void add(Pixel p) {
        this.r+=p.r;
        this.g+=p.g;
        this.b+=p.b;
        this.updateRGB();
    }
    public void subt(int num) {
        this.r-=num;
        this.g-=num;
        this.b-=num;
        this.updateRGB();
    }
    public void mul(double num) {
        this.r=(int)(this.r*num);
        this.g=(int)(this.g*num);
        this.b=(int)(this.b*num);
        this.updateRGB();
    }
    public void div(double num) {
        this.r=(int)(this.r/num);
        this.g=(int)(this.g/num);
        this.b=(int)(this.b/num);
        this.updateRGB();
    }

    private void updateRGB() {
        this.rgb=Pixel._setRGB(this.r, this.g, this.b);
    }
    private void updateIndividualRGB() {        
        this.r=Pixel.getRed(this.rgb);
        this.g=Pixel.getGreen(this.rgb);
        this.b=Pixel.getBlue(this.rgb);
    }
    private static int _setRGB(int r, int g, int b) {
        r=Math.min(r, 255);
        g=Math.min(g, 255);
        b=Math.min(b, 255);

        int rdata = r | g<<8 | b<<16;
        return rdata;
    }
    private static int getRed(int rgb) {
        int r = rgb & 0xff;
        return r;
    }
    private static int getGreen(int rgb) {
        int g = rgb>>8 & 0xff;
        return g;
    }
    private static int getBlue(int rgb) {
        int b = rgb>>16 & 0xff;
        return b;
    }
    private static int boundedIndividual(int num) {
        return Math.min(num, 255);
    }
    public String toString() {
        return "("+this.r+",\t"+this.g+",\t"+this.b+")";
    }
}
