package v2;

import processing.core.PImage;

public class Rocket extends Image {
    int speed, acc;

    public Rocket(int x, int y, PImage img) {
        super(x, y, 22, 32, img);
        this.speed = 0;
        this.acc = -1;
    }

    public void draw(Main main) {
        this.move();
        main.image(this.img, this.x, this.y);
    }

    private void move() {
        this.speed += this.acc;
        this.y += this.speed;
    }
}
