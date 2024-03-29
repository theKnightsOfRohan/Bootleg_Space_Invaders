package v2.entities;

import processing.core.PImage;
import v2.Image;
import v2.Main;

public class Rocket extends Image {
    int speed, acc;

    public Rocket(int x, int y, PImage img) {
        super(x, y, RocketSettings.WIDTH, RocketSettings.HEIGHT, img);
        this.speed = 0;
        this.acc = -1;
    }

    public void draw(Main main, int index) {
        this.move();
        main.image(this.img, this.x, this.y);
        if (this.y < -32) {
            main.rockets.remove(this);
            index--;
        }
    }

    private void move() {
        this.speed += this.acc;
        this.y += this.speed;
    }
}
