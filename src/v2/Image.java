package v2;

import processing.core.PImage;

public class Image {
    public int x, y, w, h;
    public PImage img;
    
    public Image(int x, int y, int w, int h, PImage img) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.img = img;
        img.resize(w, h);
    }

    public void draw(Main main) {
        main.image(img, x, y);
    }

    public boolean isTouching(Image img) {
        return (this.x < img.x + img.w && this.x + this.w > img.x && this.y < img.y + img.h && this.y + this.h > img.y);
    }
}
