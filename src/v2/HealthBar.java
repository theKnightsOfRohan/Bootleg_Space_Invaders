package v2;

import processing.core.PImage;

public class HealthBar extends Image {
    PImage[] imgs = new PImage[4];
    int hitCounter = 0;

    public HealthBar(PImage[] imgs) {
        super(0, 0, 100, 25, imgs[0]);
        this.imgs = imgs;
        for (PImage img : imgs) {
            img.resize(100, 25);
        }
    }

    public void draw(Main main) {
        main.image(this.imgs[this.hitCounter], this.x, this.y);
    }
}
