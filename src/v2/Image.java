package v2;

import processing.core.PImage;

public class Image {
    int x, y, w, h;
    PImage img;
    
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
}
